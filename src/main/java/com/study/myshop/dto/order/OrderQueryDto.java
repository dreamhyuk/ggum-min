package com.study.myshop.dto.order;

import com.study.myshop.domain.Order;
import com.study.myshop.domain.OrderMenu;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.OrderMenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Data
@AllArgsConstructor
public class OrderQueryDto {

    private Long orderId;
    private String storeName;
    private String customerName;
    private AddressDto address;

    private List<OrderMenuDto> orderMenus;
    private int totalPrice;

    public OrderQueryDto(Order order) {
        this.orderId = order.getId();
        this.storeName = order.getStore().getStoreName();
        this.customerName = order.getCustomerProfile().getMember().getUsername();
        this.address = new AddressDto(order.getDelivery().getAddress());
        this.orderMenus = order.getOrderMenus().stream()
                .map(orderMenu -> new OrderMenuDto(orderMenu))
                .collect(toList());
        this.totalPrice = order.getTotalPrice();
    }


}
