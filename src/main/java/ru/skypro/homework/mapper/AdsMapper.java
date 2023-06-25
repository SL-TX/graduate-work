package ru.skypro.homework.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.entity.AdsEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Named("adsToAdsDto")
    @Mapping(source = "author.id", target = "author")
    Ads entityToDto (AdsEntity entity);
    @IterableMapping(qualifiedByName = "adsToAdsDto")
    List<Ads> entitiesToDtos (List<AdsEntity> entities);

    @Mapping(source = "author", target = "author.id")
    AdsEntity dtoToEntity (Ads dto);
}
