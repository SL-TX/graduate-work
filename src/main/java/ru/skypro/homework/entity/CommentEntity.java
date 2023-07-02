package ru.skypro.homework.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @ManyToOne
    private AdsEntity ads;
    @ManyToOne
    private UserEntity user;
    private String text;
    private LocalDateTime createdAt;
}