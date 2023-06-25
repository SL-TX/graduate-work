package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsMapper adsMapper;
    private final AdsRepository adsRepository;
    @Override
    public ResponseWrapperAds getAllAds() {
        List<AdsEntity> allAds = adsRepository.findAll();
        return adsMapper.wrapAllAds(allAds.size(),allAds);
    }

    @Override
    public Ads addAd(CreateAds properties, MultipartFile image) {
        AdsEntity newAds = adsMapper.createAdsToEntity(properties);
        try {
            newAds.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return adsMapper.entityToDto(newAds);
    }
}
