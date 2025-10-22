package com.study.myshop.infrastructure.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AWSConfig {

    // aws.s3.accessKey 속성 값을 주입
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    // aws.s3.secretKey 속성 값을 주입
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3Client() {
        // 주입받은 accessKey와 secretKey로 BasicAWSCredentials 객체를 생성
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // AmazonS3ClientBuilder를 사용하여 AmazonS3 클라이언트를 빌드
        return AmazonS3ClientBuilder.standard()
                // AWS 자격 증명(Credentials)을 설정
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                // AWS 리전을 AP_NORTHEAST_2 (서울)로 설정
                .withRegion(region)
                .build();
    }


}
