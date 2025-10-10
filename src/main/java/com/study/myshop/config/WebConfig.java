package com.study.myshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이 클래스가 설정을 담당함을 알립니다.
public class WebConfig implements WebMvcConfigurer {

    // 💡 실제 이미지가 저장되는 폴더 경로
    // Spring은 파일 경로를 사용할 때 'file:' 접두사를 붙여야 합니다.
    // 파일 경로 끝에는 반드시 '/'를 붙여야 합니다.
    private String uploadPath = "file:///C:/Users/yang/Downloads/GeunFolder/study/ggum-min/uploads/";
    // 또는 "file:C:/Users/yang/Downloads/GeunFolder/study/ggum-min/uploads/"

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. 클라이언트가 "/uploads/**" (uploads 뒤의 모든 경로) 로 요청하면,
        // 2. 서버는 실제 파일 시스템의 uploadPath에서 파일을 찾아서 응답합니다.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}