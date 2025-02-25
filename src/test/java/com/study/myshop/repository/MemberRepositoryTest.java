package com.study.myshop.repository;

import com.study.myshop.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = Member.createCustomer("userA", "123", "1111-1111");

        //when
        Member saveMember = memberRepository.save(member);

        //then
        Optional<Member> foundMember = memberRepository.findById(saveMember.getId());
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getUsername()).isEqualTo("userA");
    }

}