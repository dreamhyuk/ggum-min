package com.study.myshop.annotation;

import com.study.myshop.domain.member.Role;
import com.study.myshop.security.WithCustomMockUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {
    String username() default "testOwner";
    Role role() default Role.ROLE_STORE_OWNER; // enum 사용
}