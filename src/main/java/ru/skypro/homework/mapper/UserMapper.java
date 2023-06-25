package ru.skypro.homework.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Named("userToUserDto")
    User entityToDto(UserEntity entity);
    @InheritInverseConfiguration(name = "entityToDto")
    UserEntity dtoToEntity(User dto);
}
