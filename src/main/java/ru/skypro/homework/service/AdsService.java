package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

public interface AdsService {
    ResponseWrapperAds getAllAds();

    Ads addAd(CreateAds properties, MultipartFile image, String username);

    FullAds addAds(Integer id);

    void removeAd(Integer id, String username);

    Ads updateAds(Integer id, CreateAds ads, String username);

    ResponseWrapperAds getAdsMe(String username);

    byte[] updateImage(Integer id, MultipartFile image, String username);
}
