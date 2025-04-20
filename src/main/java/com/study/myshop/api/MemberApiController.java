package com.study.myshop.api;

import com.study.myshop.service.AuthService;
import com.study.myshop.service.MemberService;
import lombok.RequiredArgsConstructor;
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
