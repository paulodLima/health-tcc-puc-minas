package com.reimbursement.health.applications.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(String keyName, InputStream inputStream, long contentLength) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, null);
        amazonS3.putObject(putObjectRequest);
        URL fileUrl = amazonS3.getUrl(bucketName, keyName);
        return fileUrl.toString();
    }
}
