package com.study.myshop.controller;

import com.study.myshop.authentication.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/owner/stores/{storeId}/orders")
    public String getStoreOrders(@PathVariable("storeId") Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "order/orderList";
    }

    @GetMapping("/customer/orders")
    public String getMyOrders(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("memberId", userDetails.getId());
        return "order/myOrderList";
    }
}
