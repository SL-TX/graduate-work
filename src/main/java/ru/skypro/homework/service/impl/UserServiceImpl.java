package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageRepository imageRepository;
    private final PasswordEncoder encoder;

    @Override
    public void setPassword(NewPassword newPassword, String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (encoder.matches(newPassword.getCurrentPassword(), userEntity.getPassword())){
            userEntity.setPassword(encoder.encode(newPassword.getNewPassword()));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bad old password");
        }
        userRepository.save(userEntity);
    }

    @Override
    public User getUser(String email) {
        return userMapper.entityToDto(userRepository.findByEmail(email));
    }

    @Override
    public User updateUser(User user, String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userMapper.entityToDto(userRepository.save(userMapper.updateEntityFromDto(user, userEntity)));
    }

    @Override
    public void updateUserImage(MultipartFile image, String username) {
        ImageEntity img = new ImageEntity();
        try {
            img.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepository.save(img);
        String link = "http://localhost:8080/images/"+img.getId().toString();
        UserEntity user = userRepository.findByEmail(username);
        user.setImage(link);
        userRepository.save(user);
    }
}
