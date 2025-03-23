package com.sankalp.user_service.clients;

import com.sankalp.user_service.dto.ParticipantCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "uploader-service", path = "/uploads")
public interface UploadServiceClient {

    @PostMapping(value = "/file/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadImage(@RequestPart("file") MultipartFile file);
}

