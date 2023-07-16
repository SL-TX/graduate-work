package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;

public interface UserService {
    void setPassword(NewPassword newPassword, String username);

    User getUser(String email);

    User updateUser(User user, String email);

    void updateUserImage(MultipartFile image, String username);
}
