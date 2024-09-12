package com.reimbursement.health.adapters.controllers;

import com.reimbursement.health.applications.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return s3Service.uploadFile(file.getOriginalFilename(), file.getInputStream(), file.getSize());
        } catch (IOException e) {
            return "Failed to upload file".concat(e.getMessage());
        }
    }
}