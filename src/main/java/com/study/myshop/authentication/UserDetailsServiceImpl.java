package com.study.myshop.authentication;

import com.study.myshop.domain.member.Member;
import com.study.myshop.exception.MemberNotFoundException;
import com.study.myshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없음."));

        //1. Member를 파라미터로 받는 생성자
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        //2. 기본 생성자 생성 후 값 세팅
//        CustomUserDetails customUserDetails2 = new CustomUserDetails();
//        customUserDetails2. ...

        return customUserDetails;
    }

    public UserDetails loadUserById(Long userId) throws MemberNotFoundException {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException("해당 유저를 찾을 수 없음."));

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        return customUserDetails;
    }

}
