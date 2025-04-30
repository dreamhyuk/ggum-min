package com.study.myshop.dto.store;

import com.study.myshop.domain.Store;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.StoreCategoryDto;
import com.study.myshop.dto.owner.OwnerDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StoreWithCategoriesDto {

    private Long storeId;
    private String storeName;
    private AddressDto address;
    private OwnerDto owner;
    private List<StoreCategoryDto> storeCategories;


    //Store와 StoreCategory를 합치는 변환 메서드
    public static StoreWithCategoriesDto from(Store store, List<StoreCategoryDto> storeCategories) {
        return new StoreWithCategoriesDto(
                store.getId(),
                store.getStoreName(),
                new AddressDto(store.getAddress()),
                new OwnerDto(store.getOwnerProfile()),
                storeCategories
        );
    }
}
