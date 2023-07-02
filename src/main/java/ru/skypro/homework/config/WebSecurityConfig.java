package ru.skypro.homework.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/ads"
    };

    @Autowired
    public DataSource dataSource;
    @Bean
    public JdbcUserDetailsManager userDetailsService() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUserExistsSql("select email from \"user\" where email = ?");
        jdbcUserDetailsManager.setCreateUserSql("insert into \"user\" (email, password, enabled) values (?,?,?)");
        jdbcUserDetailsManager.setUpdateUserSql("update \"user\" set password = ?, enabled = ? where email = ?");
        jdbcUserDetailsManager.setDeleteUserSql("delete from \"user\" where email = ?");
        jdbcUserDetailsManager.setUsersByUsernameQuery("select email,password,enabled from \"user\" where email = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select email,role from \"user\" where email = ?");
//        jdbcUserDetailsManager.setCreateAuthoritySql("update \"user\" set role=? where email = ?"); //Need workaround
        jdbcUserDetailsManager.setCreateAuthoritySql("update \"user\" set role = q1.role2 " +
                "from (select ? email2, ? role2) q1 where email = q1.email2");


        return jdbcUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        (authorization) ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)
                                        .permitAll()
                    .mvcMatchers("/ads/**", "/users/**")
                    .authenticated()
                )
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
