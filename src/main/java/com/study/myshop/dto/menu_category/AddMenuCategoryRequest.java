package com.study.myshop.dto.menu_category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddMenuCategoryRequest {

    @NotBlank(message = "카테고리 이름을 입력해 주세요.")
    private String menuCategoryName;

}
