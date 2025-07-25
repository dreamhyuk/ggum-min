//package com.study.myshop.service;
//
//import com.study.myshop.domain.Address;
//import com.study.myshop.domain.member.Member;
//import com.study.myshop.dto.customer.CustomerRequestDto;
//import com.study.myshop.dto.owner.OwnerRequestDto;
//import com.study.myshop.dto.rider.RiderRequestDto;
//import com.study.myshop.repository.MemberRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(MockitoExtension.class)
//class MemberServiceTest {
//
//    @InjectMocks MemberService memberService;
//    @InjectMocks AuthService authService;
//    @Mock
//    MemberRepository memberRepository;
//
//    @Test
//    public void 고객_회원가입_테스트() throws Exception {
//        //given
//        CustomerRequestDto request = new CustomerRequestDto("userA", "password123", "123-123",
//                "seoul", "street", "12345");
//
//        Member mockMember = Member.createCustomer("userA", "password123", "123-123",
//                new Address("seoul", "street", "12345"));
//
//        //when
//        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(mockMember);
//        System.out.println("mockMember = " + mockMember.getRole().name());
//
//        Long id = authService.joinCustomer(request);
//
//        //then
//        assertNotNull(mockMember);
//        Assertions.assertEquals("userA", mockMember.getUsername());
//        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any(Member.class));
//    }
//
//    @Test
//    public void 사장_회원가입() throws Exception {
//        //given
//        OwnerRequestDto request = new OwnerRequestDto("ownerA", "123", "1234",
//                "12345");
//
//        Member mockMember = Member.createOwner("ownerA", "123", "1234", "12345");
//
//        //when
//        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(mockMember);
//        System.out.println("mockMember = " + mockMember.getRole().name());
//
//        authService.joinOwner(request);
//
//        //then
//        assertNotNull(mockMember);
//        assertEquals("ownerA", mockMember.getUsername());
//        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any(Member.class));
//    }
//
//    @Test
//    public void 라이더_회원가입() throws Exception {
//        //given
//        RiderRequestDto request = new RiderRequestDto("riderA", "123", "1234-1234", "qwer");
//
//        Member mockMember = Member.createRider("riderA", "123", "1234-1234", "qwer");
//
//        //when
//        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(mockMember);
//        System.out.println("mockMember = " + mockMember.getRole().name());
//
//        authService.joinRider(request);
//
//        //then
//        assertNotNull(mockMember);
//        assertEquals("riderA", mockMember.getUsername());
//        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any(Member.class));
//    }
//
//}