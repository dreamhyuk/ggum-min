package com.study.myshop.repository;

import com.study.myshop.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    public List<Notification> findByReceiverId(Long userId);
}
