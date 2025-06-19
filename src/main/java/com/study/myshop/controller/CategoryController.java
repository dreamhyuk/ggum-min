package com.study.myshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping("/search/category")
    public String showCategoryPage() {
        return "search/category";
    }

//    @GetMapping("/search/category")
//    public String searchByCategory() {
//        return ""
//    }

}
