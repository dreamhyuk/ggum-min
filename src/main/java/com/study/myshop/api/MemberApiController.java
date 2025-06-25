package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.service.AuthService;
import com.study.myshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //주소 조회
    @GetMapping("/api/members/me/address")
    public ResponseEntity<ApiResponse<AddressDto>> getMyAddress(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = memberService.findByUsername(userDetails.getUsername());
        AddressDto addressDto = new AddressDto(member.getCustomerProfile().getAddress());

        return ResponseEntity.ok(ApiResponse.success(200, "배송지 조회", addressDto));
    }

}
