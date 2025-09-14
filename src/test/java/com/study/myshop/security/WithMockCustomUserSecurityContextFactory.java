/*
package com.study.myshop.security;

import com.study.myshop.annotaion.WithMockCustomUser;
import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.member.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Member member = new Member();

        // 테스트용 CustomUserDetails 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(
                customUser.username(),
                "password", // 테스트에서는 비밀번호가 중요하지 않음
                customUser.role()
        );

        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                "password",
                userDetails.getAuthorities());

        context.setAuthentication(token);
        return context;
    }
}*/
