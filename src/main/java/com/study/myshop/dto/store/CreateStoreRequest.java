package com.study.myshop.dto.store;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStoreRequest {

    private Long storeId;

    private String storeName;

    private String city;
    private String street;
    private String zipcode;

    private List<Long> categoryIds;

    //설명이나 이미지 등이 필요하면
//    private String description;
//    private String imageUrl;


    public CreateStoreRequest(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getStoreName();

        if (store.getAddress() != null) {
            this.city = store.getAddress().getCity();
            this.street = store.getAddress().getStreet();
            this.zipcode = store.getAddress().getZipcode();
        }

        this.categoryIds = store.getStoreCategoryMappings().stream()
                .map(mapping -> mapping.getStoreCategory().getId())
                .collect(Collectors.toList());
    }
}
