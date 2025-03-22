package com.study.myshop.service;

import com.study.myshop.domain.RefreshToken;
import com.study.myshop.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long save(String token, String username) {
        RefreshToken refreshToken = RefreshToken.createRefreshToken(token, username);
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getId();
    }

    public boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(refreshToken -> !refreshToken.isExpired()) //만료 여부 확인
                .orElse(false);
    }
}
