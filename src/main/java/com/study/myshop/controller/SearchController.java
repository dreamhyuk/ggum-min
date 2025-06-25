package com.study.myshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @GetMapping("/search/category")
    public String searchByCategory() {
        return "search/category";
    }

    @GetMapping("/search/name")
    public String searchByKeyword() {
        return "search/keyword";
    }



}
