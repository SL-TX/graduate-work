package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;

import java.security.Principal;

@Tag(name = "Объявления")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("ads")
public class AdsController {

    private final AdsService adsService;


    @Operation(summary = "Получить все объявления")
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(
                adsService.getAllAds()
        );
    }

    @Operation(summary = "Добавить объявление")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ads> addAd(@RequestPart CreateAds properties, @RequestPart MultipartFile image, Principal principal) {
        return ResponseEntity.ok(adsService.addAd(properties, image, principal.getName()));
    }

    @Operation(summary = "Получить информацию об объявлении")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
    })
    @GetMapping("{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adsService.addAds(id));
    }

    @Operation(summary = "Удалить объявление")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> removeAd(@PathVariable("id") Integer id, Principal principal) {
        adsService.removeAd(id, principal.getName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновить информацию об объявлении")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
    })
    @PatchMapping("{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable("id") Integer id, @RequestBody CreateAds ads, Principal principal) {
        return ResponseEntity.ok(adsService.updateAds(id, ads, principal.getName()));
    }

    @Operation(summary = "Получить объявления авторизованного пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
    })
    @GetMapping("me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(Principal principal) {
        return ResponseEntity.ok(adsService.getAdsMe(principal.getName()));
    }

    @Operation(summary = "Обновить картинку объявления")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
    })
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> updateImage(@PathVariable("id") Integer id, @RequestPart MultipartFile image, Principal principal) {
        return ResponseEntity.ok(adsService.updateImage(id, image, principal.getName()));
    }
}
