package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.AdsEntity;

import java.util.List;

public interface AdsRepository extends JpaRepository<AdsEntity, Integer> {
//    @Query("select a from AdsEntity a where a.author.id = ?1")
    List<AdsEntity> findByAuthor_Id(Integer id);
}