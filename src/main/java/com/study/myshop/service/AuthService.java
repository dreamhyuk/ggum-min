package com.study.myshop.service;

import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.login.LoginRequestDto;
import com.study.myshop.provider.JwtTokenProvider;
import com.study.myshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider; //jwt 토큰 생성 클래스

    public String authenticate(LoginRequestDto request) {
        //사용자 검증
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없음."));

        //비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        //토큰 생성
        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoleName());

        return token;
    }

}
