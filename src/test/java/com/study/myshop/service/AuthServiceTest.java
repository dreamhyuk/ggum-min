package com.study.myshop.service;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.repository.MemberRepository;
import com.study.myshop.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;


    @Test
    @DisplayName("고객 회원가입 성공")
    public void joinCustomer_Success() throws Exception {
        //given
        CustomerRequestDto request = new CustomerRequestDto("customer1", "123", "123-123",
                "city", "street", "zipcode");

        String encodedPassword = "encoded_password";
        // save() 메서드가 반환할 Member 객체를 미리 생성
        // 이 객체는 ID를 가지고 있어야 한다.
        Member member = Member.createCustomer(request.getUsername(), request.getPassword(), request.getPhoneNumber(),
                new Address(request.getCity(), request.getStreet(), request.getZipcode()));

        // DB에 저장되면 ID가 자동으로 생성되는 것을 모킹
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        // passwordEncoder.encode() 메서드가 호출되면 특정 값을 반환하도록 모킹
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        //when
        Long savedId = authService.joinCustomer(request);
        
        //then
        // 1. 반환된 ID가 예상 값과 일치하는지 확인
        assertEquals(member.getId(), savedId);

        // 2. passwordEncoder.encode() 메서드가 요청 비밀번호로 한 번 호출되었는지 확인
        verify(passwordEncoder).encode(request.getPassword());

        // 3. memberRepository.save() 메서드가 Member 객체로 한 번 호출되었는지 확인
        verify(memberRepository).save(any(Member.class));
    }




}