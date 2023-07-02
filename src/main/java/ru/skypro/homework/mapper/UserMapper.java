package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Named("userToUserDto")
    User entityToDto(UserEntity entity);

    @InheritInverseConfiguration(name = "entityToDto")
    UserEntity dtoToEntity(User dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    UserEntity updateEntityFromDto(User dto, @MappingTarget UserEntity entity);
}
