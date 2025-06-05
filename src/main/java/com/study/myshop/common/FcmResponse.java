package com.study.myshop.common;

import com.study.myshop.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FcmResponse<T> {

    private String to; //수신자 토큰
    private NotificationDto notification; //알림 정보
    private T data; //알림 내 앱 비즈니스 로직에 필요한 데이터


}
