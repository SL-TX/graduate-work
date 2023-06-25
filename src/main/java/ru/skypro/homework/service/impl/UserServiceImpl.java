package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final UserDetailsManager manager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public void setPassword(NewPassword newPassword) {
        manager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
    }

    @Override
    public User getUser() {
        return userMapper.entityToDto(userRepository.findByEmail(authentication.getName()));
    }

    @Override
    public User updateUser(User user) {
        UserEntity userEntity = userRepository.findByEmail(authentication.getName());
        return userMapper.entityToDto(userRepository.save(userMapper.updateEntityFromDto(user,userEntity)));
    }

    @Override
    public void updateUserImage(MultipartFile image) {

    }
}
