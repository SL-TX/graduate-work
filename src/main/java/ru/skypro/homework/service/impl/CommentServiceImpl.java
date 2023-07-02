package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    private void checkUserByCommentId(Integer commentId, String username){
        UserEntity user = userRepository.findByEmail(username);
        if (!Objects.equals(
                commentRepository.findById(commentId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found")).getUser().getId(), user.getId()
        ) && !user.isAdmin()) {
            throw new AccessDeniedException("Denied");
        }
    }
    @Override
    public ResponseWrapperComment getComments(Integer id) {
        List<CommentEntity> comments = commentRepository.findByAds_Pk(id);
        return commentMapper.wrapAllComments(comments.size(),comments);
    }

    @Override
    public Comment addComment(Integer id, Comment comment, String username) {
        CommentEntity entity = commentMapper.dtoToEntity(comment);
        entity.setUser(userRepository.findByEmail(username));
        entity.setAds(adsRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found")));
        entity.setCreatedAt(LocalDateTime.now());
        return commentMapper.entityToDto(commentRepository.save(entity));
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId, String username) {
        checkUserByCommentId(commentId,username);
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment, String username) {
        checkUserByCommentId(commentId,username);
        CommentEntity entity = commentRepository.findById(commentId).orElseThrow();
        return commentMapper.entityToDto(commentRepository.save(commentMapper.updateEntityFromDto(comment,entity)));
    }
}
