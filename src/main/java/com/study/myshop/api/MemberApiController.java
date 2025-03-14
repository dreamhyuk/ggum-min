package com.study.myshop.api;

import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.dto.customer.CustomerResponseDto;
import com.study.myshop.dto.owner.OwnerRequestDto;
import com.study.myshop.dto.owner.OwnerResponseDto;
import com.study.myshop.dto.rider.RiderRequestDto;
import com.study.myshop.dto.rider.RiderResponseDto;
import com.study.myshop.provider.JwtTokenProvider;
import com.study.myshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    /* 회원 가입 */
    @PostMapping("/new/customer")
    public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody @Valid CustomerRequestDto request) {
        Long id = memberService.joinCustomer(request);

        CustomerResponseDto response = new CustomerResponseDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/new/owner")
    public ResponseEntity<OwnerResponseDto> saveOwner(@RequestBody @Valid OwnerRequestDto request) {
        Long id = memberService.joinOwner(request);

        OwnerResponseDto response = new OwnerResponseDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/new/rider")
    public ResponseEntity<RiderResponseDto> saveRider(@RequestBody @Valid RiderRequestDto request) {
        Long id = memberService.joinRider(request);

        RiderResponseDto response = new RiderResponseDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* 수정 */

    /* 삭제 */

    /* 조회 */
//    @GetMapping("")
//    public ResponseEntity<MemberDto> getMember() {
//        memberService.findMembers();
//    }


}
