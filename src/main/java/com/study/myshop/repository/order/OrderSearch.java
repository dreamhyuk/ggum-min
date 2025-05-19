package com.study.myshop.repository.order;

import com.study.myshop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private OrderStatus orderStatus;

}
