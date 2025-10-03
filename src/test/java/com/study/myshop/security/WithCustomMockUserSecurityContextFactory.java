package com.study.myshop.security;

import com.study.myshop.annotation.WithCustomMockUser;
import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.authentication.UserDetailsServiceImpl;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory
        implements WithSecurityContextFactory<WithCustomMockUser> {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String username = annotation.username();
        Role role = annotation.role(); // enum 사용

        // Member 객체를 테스트용으로 직접 생성
        Member member = new Member(username, "password123", role);
        CustomUserDetails principal = new CustomUserDetails(member);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal,
                principal.getPassword(),
                principal.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        return context;
    }

/*    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String username = annotation.username();
        String role = annotation.role();

        Member mockMember = new Member(username, "password", Role.valueOf(role));
        CustomUserDetails principal = new CustomUserDetails(mockMember);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal, principal.getPassword(), principal.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        return context;
    }*/
}