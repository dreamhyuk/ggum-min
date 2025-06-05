package com.study.myshop.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = "/";

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
                redirectUrl = "/customer/home";
                break;
            } else if (authority.getAuthority().equals("ROLE_STORE_OWNER")) {
                redirectUrl = "/owner/home";
                break;
            } else if (authority.getAuthority().equals("ROLE_RIDER")) {
                redirectUrl = "/rider/home";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
