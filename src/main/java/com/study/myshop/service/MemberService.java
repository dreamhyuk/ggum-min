package com.study.myshop.service;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.dto.owner.OwnerRequestDto;
import com.study.myshop.dto.rider.RiderRequestDto;
import com.study.myshop.provider.JwtTokenProvider;
import com.study.myshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없음."));
    }


}
