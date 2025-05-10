package com.study.myshop.repository.order;

import com.study.myshop.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {

    private String customerName;
    private OrderStatus orderStatus;

}
