package com.study.myshop.dto.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MenuDto {

    private Long menuId;
    private String menuName;
    private int price;

    @JsonIgnore
    private Long menuCategoryId;

    public MenuDto(Long menuId, String menuName, int price, Long menuCategoryId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.menuCategoryId = menuCategoryId;
    }


}
