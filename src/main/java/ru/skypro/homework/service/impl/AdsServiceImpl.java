package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsMapper adsMapper;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private String uploadImage(MultipartFile image) {
        ImageEntity img = new ImageEntity();
        try {
            img.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepository.save(img);
        return "/images/" + img.getId().toString();
    }

    private void checkUserByAd(Integer adId, String username) {
        UserEntity user = userRepository.findByEmail(username);
        if (!Objects.equals(
                adsRepository.findById(adId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found")).getAuthor().getId(), user.getId()
        ) && !user.isAdmin()) {
            throw new AccessDeniedException("Denied");
        }
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<AdsEntity> allAds = adsRepository.findAll();
        return adsMapper.wrapAllAds(allAds.size(), allAds);
    }

    @Override
    public Ads addAd(CreateAds properties, MultipartFile image, String username) {
        AdsEntity newAds = adsMapper.createAdsToEntity(properties);
        newAds.setImage(uploadImage(image));
        newAds.setAuthor(userRepository.findByEmail(username));
        adsRepository.save(newAds);
        return adsMapper.entityToDto(newAds);
    }

    @Override
    public FullAds addAds(Integer id) {
        AdsEntity ads = adsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        return adsMapper.entityToFullAdsDto(ads);
    }

    @Override
    public void removeAd(Integer id, String username) {
        checkUserByAd(id, username);
        adsRepository.deleteById(id);
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads, String username) {
        checkUserByAd(id, username);
        AdsEntity ad = adsRepository.findById(id).orElseThrow();
        return adsMapper.entityToDto(adsRepository.save(adsMapper.updateEntityFromCreateAdsDto(ads, ad)));
    }

    @Override
    public ResponseWrapperAds getAdsMe(String username) {
        List<AdsEntity> ads = adsRepository.findByAuthor_Email(username);
        return adsMapper.wrapAllAds(ads.size(), ads);
    }

    @SneakyThrows
    @Override
    public byte[] updateImage(Integer id, MultipartFile image, String username) {
        checkUserByAd(id, username);
        AdsEntity ads = adsRepository.findById(id).orElseThrow();
        ads.setImage(uploadImage(image));
        adsRepository.save(ads);
        return image.getBytes();
    }
}
