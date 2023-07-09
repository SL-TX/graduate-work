package ru.skypro.homework.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ads")
public class AdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @ManyToOne
    private UserEntity author;
    private String title;
    private String description;
    private Integer price;
    private String image;
    @OneToMany(mappedBy = "ads",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CommentEntity> comments;
}
