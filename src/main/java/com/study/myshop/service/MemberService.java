package com.study.myshop.service;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.dto.owner.OwnerRequestDto;
import com.study.myshop.dto.rider.RiderRequestDto;
import com.study.myshop.provider.JwtTokenProvider;
import com.study.myshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public Long joinCustomer(CustomerRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());

        Member member = Member.createCustomer(request.getUsername(), encodedPassword, request.getPhoneNumber(), address);
//        Member member = Member.createCustomer2(request);

        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long joinOwner(OwnerRequestDto request) {
        Member member = Member.createOwner2(request);
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Long joinRider(RiderRequestDto request) {
        Member member = Member.createRider2(request);
        memberRepository.save(member);

        return member.getId();
    }





}
