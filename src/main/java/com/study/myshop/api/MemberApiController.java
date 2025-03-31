package com.study.myshop.api;

import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.dto.customer.CustomerResponseDto;
import com.study.myshop.dto.owner.OwnerRequestDto;
import com.study.myshop.dto.owner.OwnerResponseDto;
import com.study.myshop.dto.rider.RiderRequestDto;
import com.study.myshop.dto.rider.RiderResponseDto;
import com.study.myshop.provider.JwtTokenProvider;
import com.study.myshop.service.AuthService;
import com.study.myshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final AuthService authService;


    /* 수정 */

    /* 삭제 */

    /* 조회 */
//    @GetMapping("")
//    public ResponseEntity<MemberDto> getMember() {
//        memberService.findMembers();
//    }


}
