package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class ResponseWrapperAds {
    @Schema(description = "общее количество объявлений")
    private Integer count;
    private List<Ads> results;
}
