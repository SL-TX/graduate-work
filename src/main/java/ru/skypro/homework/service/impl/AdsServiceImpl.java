package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsMapper adsMapper;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseWrapperAds getAllAds() {
        List<AdsEntity> allAds = adsRepository.findAll();
        return adsMapper.wrapAllAds(allAds.size(),allAds);
    }

    @Override
    public Ads addAd(CreateAds properties, MultipartFile image) {
        AdsEntity newAds = adsMapper.createAdsToEntity(properties);
        newAds.setImage(image.getOriginalFilename());//TODO:
        newAds.setAuthor(userRepository.findById(1).get());//TODO:
        adsRepository.save(newAds);
        return adsMapper.entityToDto(newAds);
    }

    @Override
    public FullAds addAds(Integer id) {
        AdsEntity ads = adsRepository.findById(id).orElseThrow(()->new RuntimeException("Id not found"));
        return adsMapper.entityToFullAdsDto(ads);
    }

    @Override
    public void removeAd(Integer id) {
        adsRepository.deleteById(id);
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads) {
        AdsEntity ad = adsRepository.findById(id).orElseThrow(()->new RuntimeException("Id not found"));
        ad.setPrice(ads.getPrice());
        ad.setTitle(ads.getTitle());
        ad.setDescription(ads.getDescription());
        adsRepository.save(ad);
        return adsMapper.entityToDto(ad);
    }

    @Override
    public ResponseWrapperAds getAdsMe() {
        List<AdsEntity> ads = adsRepository.findByAuthor_Id(1);
        return adsMapper.wrapAllAds(ads.size(),ads);
    }

    @SneakyThrows
    @Override
    public byte[] updateImage(Integer id, MultipartFile image) {
        AdsEntity ads = adsRepository.findById(id).orElseThrow(()->new RuntimeException("Id not found"));
        return image.getBytes();
    }
}
