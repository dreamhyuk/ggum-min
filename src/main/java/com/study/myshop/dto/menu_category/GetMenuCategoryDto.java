package com.study.myshop.dto.menu_category;

import com.study.myshop.domain.category.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMenuCategoryDto {

    private Long menuCategoryId;
    private String menuCategoryName;

    public GetMenuCategoryDto(MenuCategory menuCategory) {
        this.menuCategoryId = menuCategory.getId();
        this.menuCategoryName = menuCategory.getName();
    }
}
