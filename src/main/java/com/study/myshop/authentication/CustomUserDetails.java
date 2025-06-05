package com.study.myshop.authentication;

import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private Role role;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.authorities = List.of(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password; // JWT 기반 인증에서는 비밀번호를 사용하지 않음
    }

}
