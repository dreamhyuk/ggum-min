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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;


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
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request, BindingResult bindingResult,
                                                  HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CustomUserDetails customUserDetails = authService.authenticate(request);

        //redirect url
        String redirectUrl = customUserDetails.getRole().getRedirectUrlByRole();

        //토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(customUserDetails.getId(), customUserDetails.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(customUserDetails.getId());

        //refreshToken 은 repository 저장
        refreshTokenService.save(refreshToken, customUserDetails.getUsername());

        // Access Token -> HttpOnly Cookie로 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/"); // 모든 경로에 대해 쿠키 전송
        accessTokenCookie.setMaxAge(60 * 60); // 예: 1시간

        // Refresh Token
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 전용
        refreshTokenCookie.setPath("/api/auth/refresh"); // 경로 제한
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일

        // 쿠키 응답에 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        ResponseCookie.from("accessToken", accessToken)
                                .httpOnly(false) // true면 JS 접근 불가
                                .secure(false) // HTTPS 아니면 false
                                .sameSite("Lax") // 또는 "Strict"
                                .path("/")
                                .maxAge(Duration.ofHours(1))
                                .build().toString()
                )
                .body(new LoginResponseDto(redirectUrl, accessToken));

//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .body(new LoginResponseDto(redirectUrl, refreshToken));
    }

}
