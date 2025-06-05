package com.study.myshop.domain;

import com.study.myshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {

    @Id @GeneratedValue
    @Column(name = "fcm_token_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, unique = true)
    private String token;

    public FcmToken create(Member member, String token) {
        FcmToken fcmToken = new FcmToken();
        fcmToken.member = member;
        fcmToken.token = token;

        return fcmToken;
    }

}
