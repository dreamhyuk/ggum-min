package com.study.myshop.dto;

import com.study.myshop.domain.category.StoreCategory;
import lombok.Getter;

@Getter
public class StoreCategoryDto {

    private Long storeCategoryId;
    private String name;

    public StoreCategoryDto(Long storeCategoryId, String name) {
        this.storeCategoryId = storeCategoryId;
        this.name = name;
    }

    public StoreCategoryDto(StoreCategory storeCategory) {
        this.storeCategoryId = storeCategory.getId();
        this.name = storeCategory.getName();
    }
}
