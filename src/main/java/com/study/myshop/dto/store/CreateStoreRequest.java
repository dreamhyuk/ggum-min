package com.study.myshop.dto.store;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CreateStoreRequest {

    private String storeName;

    private String city;
    private String street;
    private String zipcode;

    private List<Long> categoryIds;

    //설명이나 이미지 등이 필요하면
//    private String description;
//    private String imageUrl;
}
