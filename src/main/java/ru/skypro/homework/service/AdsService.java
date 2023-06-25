package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

public interface AdsService {
    ResponseWrapperAds getAllAds();

    Ads addAd(CreateAds properties, MultipartFile image);

    FullAds addAds(Integer id);

    void removeAd(Integer id);

    Ads updateAds(Integer id, CreateAds ads);

    ResponseWrapperAds getAdsMe();

    byte[] updateImage(Integer id, MultipartFile image);
}
