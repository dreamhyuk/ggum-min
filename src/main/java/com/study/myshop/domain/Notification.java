package com.study.myshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private Long receiverId; //customer, owner, rider

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String title;
    private String message;

//    private boolean read = false;

    public static Notification createNotification(Long receiverId, NotificationType type, String title, String message) {
        Notification notification = new Notification();
        notification.receiverId = receiverId;
        notification.notificationType = type;
        notification.title = title;
        notification.message = message;

        return notification;
    }

//    public void changeRead() {
//        this.read = true;
//    }

}
