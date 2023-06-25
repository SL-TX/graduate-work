package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    @Override
    public ResponseWrapperComment getComments(Integer id) {
        List<CommentEntity> comments = commentRepository.findByAds_Pk(id);
        return commentMapper.wrapAllComments(comments.size(),comments);
    }

    @Override
    public Comment addComment(Integer id, Comment comment) {
        CommentEntity entity = commentMapper.dtoToEntity(comment);
        entity.setAds(adsRepository.findById(id).orElseThrow(()->new RuntimeException("Id not found")));
        return commentMapper.entityToDto(commentRepository.save(entity));
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment) {
        CommentEntity entity = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("Id not found"));
        return commentMapper.entityToDto(commentRepository.save(commentMapper.updateEntityFromDto(comment,entity)));
    }
}
