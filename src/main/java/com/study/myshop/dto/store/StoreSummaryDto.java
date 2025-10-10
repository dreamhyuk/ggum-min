package com.study.myshop.dto.store;

import com.study.myshop.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreSummaryDto {

    private Long storeId;
    private String storeName;
    private String imageUrl;

    /*
    이미지, 평점, 최소 주문 금액 등..
     */


    public StoreSummaryDto(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getStoreName();
        this.imageUrl = store.getImageUrl();
    }
}
