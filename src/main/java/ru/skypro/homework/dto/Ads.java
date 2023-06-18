package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Ads {
    //@Schema(description = "id автора объявлени")
    private Integer author;
    //@Schema(description = "ссылка на картинку объявления")
    private String image;
    //@Schema(description = "id объявления")
    private Integer pk;
    //@Schema(description = "цена объявления")
    private Integer price;
    //@Schema(description = "заголовок объявления")
    private String title;
}
