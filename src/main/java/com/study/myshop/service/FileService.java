package com.study.myshop.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.study.myshop.dto.FileRequestDto;
import com.study.myshop.dto.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public PresignedUrlResponse getPresignedUrl(FileRequestDto request) {
        // 만료 시간 설정 (예: 5분)
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5);

        // S3에 presigned URL 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, request.getFileName())
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        // 실제 접근 가능한 URL (DB나 클라이언트용)
        String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + request.getFileName();

        return new PresignedUrlResponse(url.toString(), fileUrl);
    }
}
