package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.ImageEntity;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
}