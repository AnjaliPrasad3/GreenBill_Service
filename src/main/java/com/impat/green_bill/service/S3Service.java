package com.impat.green_bill.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface S3Service {
    String uploadFileToS3(MultipartFile file) throws IOException;
    Resource downloadFileFromS3(String fileUrl);
}
