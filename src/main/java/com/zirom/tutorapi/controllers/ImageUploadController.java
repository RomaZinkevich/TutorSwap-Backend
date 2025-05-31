package com.zirom.tutorapi.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

        File destination = new File(directory, image.getOriginalFilename());
        image.transferTo(destination);

        return ResponseEntity.ok("Saved to: " + destination.getAbsolutePath());
    }
}
