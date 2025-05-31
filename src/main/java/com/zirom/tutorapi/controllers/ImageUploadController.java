package com.zirom.tutorapi.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController("/api/v1")
public class ImageUploadController {

    @Value("${FILE_STORAGE_PATH}")
    private String BASE_PATH;

    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> handleImageUpload(@RequestParam("image") MultipartFile image) throws IOException {
        File directory = new File(BASE_PATH);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return ResponseEntity.status(500).body("Failed to create directory.");
            }
        }
        if (!Objects.requireNonNull(image.getOriginalFilename()).endsWith(".jpeg")) {
            throw new BadRequestException("Unsupported image type");
        }
        if (image.getSize() > 15 * 1024 * 1024) {
            throw new BadRequestException("Too large image size");
        }

        String uniqueFilename = UUID.randomUUID().toString() + ".jpeg";
        File destination = new File(directory, uniqueFilename);
        image.transferTo(destination);

        return ResponseEntity.ok(uniqueFilename);
    }
}
