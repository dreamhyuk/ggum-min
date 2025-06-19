package com.study.myshop.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreInfoRequest {

    private String storeName;

    private String city;
    private String street;
    private String zipcode;

    private List<Long> categoryIds;

}
