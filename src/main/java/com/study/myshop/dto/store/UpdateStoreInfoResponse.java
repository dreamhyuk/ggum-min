package com.study.myshop.dto.store;

import com.study.myshop.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStoreInfoResponse {

    private Long storeId;
    private String storeName;
    private AddressDto address;

}
