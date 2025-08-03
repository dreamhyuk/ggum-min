package com.study.myshop.repository;

import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired AuthService authService;

    @Test
    public void 고객_회원가입() throws Exception {
        //given
        CustomerRequestDto request = new CustomerRequestDto("userA", "123", "123-123",
                "city", "street", "zipcode");

        //when
        Long saveId = authService.joinCustomer(request);

        //then
        Member foundMember = memberRepository.findById(saveId).get();
        assertEquals(saveId, foundMember.getId());
    }

    @Test
    public void testJoin() throws Exception {
        //given
        CustomerRequestDto requestDto = new CustomerRequestDto("userA", "123", "123-123",
                "city", "street", "zipcode");


        //when
        Long id = authService.joinCustomer(requestDto);
        Member saveMember = memberRepository.findOneById(id);

        System.out.println("id = " + id);
        System.out.println("saveMember = " + saveMember.getId());

        //then
        org.junit.jupiter.api.Assertions.assertEquals(id, saveMember.getId());
    }

}