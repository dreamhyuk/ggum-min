package com.study.myshop.dto.store;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.MenuCategoryDto;
import com.study.myshop.dto.StoreCategoryDto;
import com.study.myshop.dto.owner.OwnerDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class StoreResponseDto {

    private Long storeId;
    private String name;
    private AddressDto storeAddress;
    private OwnerDto owner;
    private List<StoreCategoryDto> storeCategories;

    private List<MenuCategoryDto> menuCategories;

    //store + storeCategory + menuCategory 를 합치는 변환 메서드
    public static StoreResponseDto from(Store store,
                                        List<StoreCategoryDto> storeCategoryDtos,
                                        List<MenuCategoryDto> menuCategoryDtos) {

        return new StoreResponseDto(
                store.getId(),
                store.getStoreName(),
                new AddressDto(store.getAddress()),
                new OwnerDto(store.getOwnerProfile()),
                storeCategoryDtos,
                menuCategoryDtos
        );
    }

}
