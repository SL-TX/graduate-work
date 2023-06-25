package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.ResponseWrapperComment;

public interface CommentService {
    ResponseWrapperComment getComments(Integer id);

    Comment addComment(Integer id, Comment comment);

    void deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, Comment comment);
}
