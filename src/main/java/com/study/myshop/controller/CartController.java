package com.study.myshop.controller;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Cart;
import com.study.myshop.domain.member.Member;
import com.study.myshop.repository.CartRepository;
import com.study.myshop.service.CartService;
import com.study.myshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final MemberService memberService;
    private final CartRepository cartRepository;

    @GetMapping("/customer/cart")
    public String myCart(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Member member = memberService.findByUsername(userDetails.getUsername());

        Cart cart = cartRepository.findByCustomerId(member.getCustomerProfile().getId())
                .orElseThrow(() -> new IllegalStateException("장바구니 없음"));

        Long storeId = cart.getCartItems().get(0).getMenu().getMenuCategory().getStore().getId();

        model.addAttribute("storeId", storeId);

        return "myCart";
    }
}
