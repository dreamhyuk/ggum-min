package com.study.myshop.dto.menu;

import lombok.Getter;

@Getter
public class UpdateMenuRequest {

    private String menuName;
    private int price;
    private Long menuCategoryId;

}
