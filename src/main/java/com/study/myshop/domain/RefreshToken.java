package com.study.myshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    private String token;

    private String username;

    private LocalDateTime expiryDate;

    public static RefreshToken createRefreshToken(String token, String username) {
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);
        return new RefreshToken(token, username, expiryDate);
    }

    public RefreshToken(String token, String username, LocalDateTime expiryDate) {
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }

    //만료 여부 검증
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

}
