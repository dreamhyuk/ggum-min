package com.study.myshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/join")
    public String signup() {
        return "members/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "members/login";
    }
}
