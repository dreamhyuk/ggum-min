package com.study.myshop.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String redirectUrl;
    private String accessToken;
//    private String refreshToken;
}
