package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.Cart;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.cart.CartDto;
import com.study.myshop.dto.cart.CartRequest;
import com.study.myshop.repository.CartRepository;
import com.study.myshop.service.CartService;
import com.study.myshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartApiController {

    private final MemberService memberService;
    private final CartRepository cartRepository;
    private final CartService cartService;

/*    @GetMapping("/carts/me")
    public ResponseEntity<ApiResponse<CartDto>> getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member findMember = memberService.findByUsername(userDetails.getUsername());

        Cart cart = cartRepository.findByCustomerId(findMember.getCustomerProfile().getId())
                .orElseThrow(() -> new IllegalArgumentException("cart not found"));

        CartDto cartDto = new CartDto(cart);

        return ResponseEntity.ok(ApiResponse.success(200, "장바구니 조회", cartDto));
    }*/
    @GetMapping("/carts/me")
    public ResponseEntity<ApiResponse<CartDto>> getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Member member = memberService.findByUsername(userDetails.getUsername());

        return cartRepository.findByCustomerId(member.getCustomerProfile().getId())
                .map(cart -> ResponseEntity.ok(
                        ApiResponse.success(200, "장바구니 조회", new CartDto(cart))))
                // 장바구니가 없으면 204 No Content + body null
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/carts/me")
    public ResponseEntity<ApiResponse<Long>> createCart(@RequestBody CartRequest request,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long cartId = cartService.addMenuToCart(userDetails.getId(), request);

        return ResponseEntity.ok(ApiResponse.success(200, "장바구니 생성", cartId));
    }
}
