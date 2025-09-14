package com.study.myshop.dto.store;

import com.study.myshop.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreInfoRequest {

    private String storeName;

    private AddressDto addressDto;

    private List<Long> categoryIds;


}
