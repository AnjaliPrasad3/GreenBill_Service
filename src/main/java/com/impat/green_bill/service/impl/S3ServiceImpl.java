package com.impat.green_bill.service.impl;

import com.impat.green_bill.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;

    private final String BUCKET_NAME = "greenbill";

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFileToS3(MultipartFile file) throws IOException {
        String key = "bills/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + key;
    }

    @Override
    public Resource downloadFileFromS3(String fileUrl) {
        // Extract key from full URL
        String key = fileUrl.substring(fileUrl.indexOf("bills/"));

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();

        InputStream inputStream = s3Client.getObject(getObjectRequest);
        return new InputStreamResource(inputStream);
    }
}
