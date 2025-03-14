package com.study.myshop.api;

import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.login.LoginRequestDto;
import com.study.myshop.dto.login.LoginResponseDto;
import com.study.myshop.repository.MemberRepository;
import com.study.myshop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        String token = authService.authenticate(request);

        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        String redirectUrl = member.getRole().getRedirectUrlByRole();

        return ResponseEntity.ok(new LoginResponseDto(token, redirectUrl));
    }

}
