package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.CommentEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, Instant.class, ZoneId.class})
public interface CommentMapper {
    @Named("commentToCommentDto")
    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())")
    Comment entityToDto(CommentEntity entity);

    @Named("commentsToCommentDtos")
    @IterableMapping(qualifiedByName = "commentToCommentDto")
    List<Comment> entitiesToDtos(List<CommentEntity> entities);

    @Mapping(source = "author", target = "user.id")
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "createdAt", expression = "java(LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.getCreatedAt()), ZoneId.systemDefault()))")
    CommentEntity dtoToEntity(Comment dto);

    @Mapping(target = "count", source = "size")
    @Mapping(target = "results", source = "comments", qualifiedByName = "commentsToCommentDtos")
    ResponseWrapperComment wrapAllComments(int size, List<CommentEntity> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CommentEntity updateEntityFromDto(Comment dto, @MappingTarget CommentEntity entity);
}
