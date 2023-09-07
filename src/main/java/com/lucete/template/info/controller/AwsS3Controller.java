package com.lucete.template.info.controller;

import com.lucete.template.info.DTO.AwsS3DTO;
import com.lucete.template.info.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/resource")
    public AwsS3DTO upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return awsS3Service.upload(multipartFile,"upload");
    }

    @DeleteMapping("/resource")
    public void remove(AwsS3DTO awsS3) {
        awsS3Service.remove(awsS3);
    }
}