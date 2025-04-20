package com.study.myshop.dto;

import com.study.myshop.domain.category.MenuCategory;

public class MenuCategoryDto {

    private Long id;
    private String name;

    public MenuCategoryDto(MenuCategory menuCategory) {
        this.id = menuCategory.getId();
        this.name = menuCategory.getName();
    }
}
