package com.study.myshop.domain.member;

import lombok.AllArgsConstructor;

public enum Role {
    ROLE_CUSTOMER("/customer/home"),
    ROLE_RIDER("/rider/home"),
    ROLE_STORE_OWNER("/owner/home");

    private final String redirectUrl;

    Role (String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrlByRole() {
        return redirectUrl;
    }

}
