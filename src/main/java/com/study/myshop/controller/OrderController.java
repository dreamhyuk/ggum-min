package com.study.myshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/owner/stores/{storeId}/orders")
    public String getOrders(@PathVariable("storeId") Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "order/orderList";
    }
}
