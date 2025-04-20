package com.study.myshop.service;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.dto.login.LoginRequestDto;
import com.study.myshop.dto.owner.OwnerRequestDto;
import com.study.myshop.dto.rider.RiderRequestDto;
import com.study.myshop.security.JwtTokenProvider;
import com.study.myshop.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * 회원 가입
     */
    @Transactional
    public Long joinCustomer(CustomerRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());

        Member member = Member.createCustomer(request.getUsername(), encodedPassword, request.getPhoneNumber(), address);

        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long joinOwner(OwnerRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.createOwner(request.getUsername(), encodedPassword, request.getPhoneNumber(), request.getBusinessNumber());
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long joinRider(RiderRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.createRider(request.getUsername(), encodedPassword, request.getPhoneNumber(), request.getVehicleType());
        memberRepository.save(member);

        return member.getId();
    }


    /**
     * 로그인 검증
     */
    public CustomUserDetails authenticate(LoginRequestDto request) {
        //사용자 검증
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없음."));

        //비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        return customUserDetails;
    }

    /**
     * jwt 인증 처리
     */
    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                //secretKey를 직접 입력했지만, 환경 변수나 설정 파일(application.yml)에서 읽어오는 방식을 사용.
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없음."));

        return jwtTokenProvider.getAuthentication(token, member);

    }

}
