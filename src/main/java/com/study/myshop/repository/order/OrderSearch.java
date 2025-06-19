package com.study.myshop.repository.order;

import com.study.myshop.domain.OrderStatus;
import lombok.Data;
import lombok.Setter;

@Data
public class OrderSearch {

    private OrderStatus orderStatus;

}
