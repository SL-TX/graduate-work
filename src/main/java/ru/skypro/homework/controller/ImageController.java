package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

@Tag(name = "Картинки")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("images")
public class ImageController {

    private final ImageService imageService;
    @GetMapping(value = "{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public byte[] getImage(@PathVariable String id) {
        return imageService.getImage(id);
    }
}
