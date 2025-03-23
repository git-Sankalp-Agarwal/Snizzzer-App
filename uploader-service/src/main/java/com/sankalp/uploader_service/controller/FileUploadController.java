package com.sankalp.uploader_service.controller;


import com.sankalp.uploader_service.service.FileUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploaderService fileUploaderService;

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        try {
            return fileUploaderService.upload(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
