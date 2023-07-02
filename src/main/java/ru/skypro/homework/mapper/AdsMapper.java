package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Named("adsToAdsDto")
    @Mapping(source = "author.id", target = "author")
    Ads entityToDto(AdsEntity entity);

    @Named("adsToAdsDtos")
    @IterableMapping(qualifiedByName = "adsToAdsDto")
    List<Ads> entitiesToDtos(List<AdsEntity> entities);

    @Mapping(source = "author", target = "author.id")
    AdsEntity dtoToEntity(Ads dto);

    @Mapping(target = "results", source = "allAds", qualifiedByName = "adsToAdsDtos")
        //@Mapping(target = "count", expression = "java(allAds.size)")
    ResponseWrapperAds wrapAllAds(Integer count, List<AdsEntity> allAds);

    AdsEntity createAdsToEntity(CreateAds properties);

    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    @Mapping(source = "author.email", target = "email")
    @Mapping(source = "author.phone", target = "phone")
    FullAds entityToFullAdsDto(AdsEntity entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdsEntity updateEntityFromCreateAdsDto(CreateAds dto, @MappingTarget AdsEntity entity);
}
