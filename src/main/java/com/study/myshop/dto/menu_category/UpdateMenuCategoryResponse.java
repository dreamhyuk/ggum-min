package com.study.myshop.dto.menu_category;

import com.study.myshop.domain.category.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMenuCategoryResponse {

    private Long menuCategoryId;
    private String menuCategoryName;

    public UpdateMenuCategoryResponse(MenuCategory menuCategory) {
        this.menuCategoryId = menuCategory.getId();
        this.menuCategoryName = menuCategory.getName();
    }
}
