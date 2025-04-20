package com.study.myshop.dto.store;

import lombok.Data;

@Data
public class UpdateStoreInfoRequest {

    private String storeName;

    private String city;
    private String street;
    private String zipcode;

}
