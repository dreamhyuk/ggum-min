package com.study.myshop.dto.order;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.OrderStatus;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.OrderMenuDto;
import com.study.myshop.dto.OrderMenuRequest;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
public class CreateOrderRequest {

    private AddressDto addressDto;
    private List<OrderMenuRequest> orderMenus;

//    public CreateOrderRequest(AddressDto addressDto, List<OrderMenuDto> orderMenus) {
//        this.addressDto = addressDto;
//
//
//    }

//    public CreateOrderRequest(Order order) {
//        this.addressDto = new AddressDto(order.getCustomerProfile().getAddress());
//        this.orderMenuDtos = order.getOrderMenus().stream()
//                .map(orderMenu -> new OrderMenuDto(orderMenu))
//                .collect(toList());
//    }
}
