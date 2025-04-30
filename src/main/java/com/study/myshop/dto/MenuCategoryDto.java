package com.study.myshop.dto;

import com.study.myshop.dto.menu.MenuDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class MenuCategoryDto {

    private Long menuCategoryId;
    private String name;
    private List<MenuDto> menus;

    public MenuCategoryDto(Long menuCategoryId, String name) {
        this.menuCategoryId = menuCategoryId;
        this.name = name;
        this.menus = new ArrayList<>();
    }

    public MenuCategoryDto(Long menuCategoryId, String name, List<MenuDto> menus) {
        this.menuCategoryId = menuCategoryId;
        this.name = name;
        this.menus = menus;
    }

}
