package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserManager implements UserDetailsManager {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Override
    public void createUser(UserDetails user) {
        UserEntity newUser = new UserEntity();
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getUsername());
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        UserEntity oldUser = userRepository.findByEmail(user.getUsername());
        oldUser.setPassword(user.getPassword());
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByEmail(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }
}
