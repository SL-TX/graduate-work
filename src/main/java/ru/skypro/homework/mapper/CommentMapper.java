package ru.skypro.homework.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Named("commentToCommentDto")
    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.image", target = "authorImage")
    Comment entityToDto (CommentEntity entity);
    @IterableMapping(qualifiedByName = "commentToCommentDto")
    List<Comment> entitiesToDtos (List<CommentEntity> entities);

    @Mapping(source = "author", target = "user.id")
    CommentEntity dtoToEntity (Comment dto);
}
