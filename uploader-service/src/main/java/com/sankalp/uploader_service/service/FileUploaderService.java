package com.sankalp.uploader_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploaderService {

    String upload(MultipartFile file) throws IOException;
}
