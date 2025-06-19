package com.study.myshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/new/customers")
    public String createCustomerForm() {
        return "members/createCustomerForm";
    }

    @GetMapping("/new/owners")
    public String createOwnerForm() {
        return "members/createOwnerForm";
    }

    @GetMapping("new/riders")
    public String createRiderForm() {
        return "members/createRiderForm";
    }

    @GetMapping("/customer/home")
    public String customerHome() {
        return "customerHome";
    }

    @GetMapping("/owner/home")
    public String ownerHome() {
        return "ownerHome";
    }

    @GetMapping("/rider/home")
    public String riderHome() {
        return "riderHome";
    }

}
