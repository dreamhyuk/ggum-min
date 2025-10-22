package com.study.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresignedUrlResponse {

    private String presignedUrl; // PUT 요청에 사용할 URL
    private String fileUrl;      // 실제 접근 가능한 S3 URL (DB 저장용)
}
