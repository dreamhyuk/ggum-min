package com.study.myshop.service;

import com.study.myshop.domain.Notification;
import com.study.myshop.domain.NotificationType;
import com.study.myshop.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private NotificationRepository notificationRepository;

    @Transactional
    public Long save(Long receiverId, NotificationType type, String title, String message) {
        Notification notification = Notification.createNotification(receiverId, type, title, message);

        notificationRepository.save(notification);

        return notification.getId();

        //send 로직을 추가(fcm)
    }


    public List<Notification> getMyNotifications(Long userId) {
        return notificationRepository.findByReceiverId(userId);
    }

    public Notification getNotification(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림 없음."));

        if (!notification.getReceiverId().equals(userId)) {
            throw new AccessDeniedException("자신의 알림만 조회할 수 있습니다.");
        }

        return notification;
    }


}
