package com.study.myshop.dto.store;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.StoreCategoryMappingDto;
import com.study.myshop.dto.owner.OwnerDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
public class StoreResponseDto {

    private Long storeId;
    private String name;
    private Address storeAddress;
    private OwnerDto owner;
    private List<StoreCategoryMappingDto> mappings;

    public StoreResponseDto(Store store) {
        storeId = store.getId();
        name = store.getStoreName();
        storeAddress = store.getAddress();
        owner = new OwnerDto(store.getOwnerProfile());
        mappings = store.getStoreCategoryMappings().stream()
                .map(storeCategoryMapping -> new StoreCategoryMappingDto(storeCategoryMapping))
                .collect(toList());

    }


}
