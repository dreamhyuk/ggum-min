package com.study.myshop.dto;

import com.study.myshop.domain.StoreCategoryMapping;
import lombok.Getter;

@Getter
public class StoreCategoryMappingDto {

    private Long categoryId;
    private String categoryName;

    public StoreCategoryMappingDto(StoreCategoryMapping mapping) {
        categoryId = mapping.getStoreCategory().getId();
        categoryName = mapping.getStoreCategory().getName();
    }
}
