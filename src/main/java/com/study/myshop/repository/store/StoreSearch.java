package com.study.myshop.repository.store;

import lombok.Getter;

@Getter
public class StoreSearch {

    private String storeName; //가게 이름
    private String storeCategory;

    //정렬 기준, 최소 주문 금액 필터 등이 추가될 수도 있다.
//    private String orderBy;
//    private String minOrderPrice;
}
