package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;

public interface UserService {
    void setPassword(NewPassword newPassword);

    User getUser();

    User updateUser(User user);

    void updateUserImage(MultipartFile image);
}
