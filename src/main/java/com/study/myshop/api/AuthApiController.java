package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.dto.customer.CustomerResponseDto;
import com.study.myshop.dto.login.LoginRequestDto;
import com.study.myshop.dto.login.LoginResponseDto;
import com.study.myshop.dto.owner.OwnerRequestDto;
import com.study.myshop.dto.owner.OwnerResponseDto;
import com.study.myshop.dto.rider.RiderRequestDto;
import com.study.myshop.dto.rider.RiderResponseDto;
import com.study.myshop.security.JwtTokenProvider;
import com.study.myshop.service.AuthService;
import com.study.myshop.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;


    /**
     * 회원 가입
     */
    @PostMapping("/new/customer")
    public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody @Valid CustomerRequestDto request) {
        Long id = authService.joinCustomer(request);

        CustomerResponseDto response = new CustomerResponseDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/new/owner")
    public ResponseEntity<OwnerResponseDto> saveOwner(@RequestBody @Valid OwnerRequestDto request) {
        Long id = authService.joinOwner(request);

        OwnerResponseDto response = new OwnerResponseDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/new/rider")
    public ResponseEntity<RiderResponseDto> saveRider(@RequestBody @Valid RiderRequestDto request) {
        Long id = authService.joinRider(request);

        RiderResponseDto response = new RiderResponseDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CustomUserDetails customUserDetails = authService.authenticate(request);

        //redirect url
        String redirectUrl = customUserDetails.getRole().getRedirectUrlByRole();

        //access 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(customUserDetails.getId(), customUserDetails.getRole());

        //refresh 토큰 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(customUserDetails.getId());
        refreshTokenService.save(refreshToken, customUserDetails.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(new LoginResponseDto(redirectUrl, refreshToken));
    }

}
