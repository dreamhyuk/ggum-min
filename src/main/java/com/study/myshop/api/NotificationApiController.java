package com.study.myshop.api;

import com.study.myshop.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationApiController {

    private NotificationService notificationService;

//    //알림 목록 조회
//    @GetMapping("/notifications")
//    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationList() {
//
//    }
//
//    //알림 상세 조회
//    @GetMapping("/notifications/{id}")
//    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification() {
//
//    }

}
