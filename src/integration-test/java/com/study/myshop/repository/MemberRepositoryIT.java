package com.study.myshop.repository;

import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class MemberRepositoryIT {

    @Autowired MemberRepository memberRepository;
    @Autowired AuthService authService;


    @Test
    @DisplayName("회원가입 후 ID로 멤버 조회 확인")
    public void 고객_회원가입() throws Exception {
        //given
        CustomerRequestDto requestDto = new CustomerRequestDto("userA", "123", "123-123",
                "city", "street", "zipcode");

        //when
        Long id = authService.joinCustomer(requestDto);
        Member saveMember = memberRepository.findOneById(id);

        System.out.println("id = " + id);
        System.out.println("saveMember = " + saveMember.getId());

        //then
        assertEquals(id, saveMember.getId());
    }
}
