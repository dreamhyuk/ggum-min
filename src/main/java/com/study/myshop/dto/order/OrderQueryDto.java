package com.study.myshop.dto.order;

import com.study.myshop.domain.DeliveryStatus;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.OrderStatus;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.OrderMenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.*;

@Data
@AllArgsConstructor
public class OrderQueryDto {

    private Long orderId;
    private String storeName;
    private String customerName;
    private AddressDto address;

    private List<OrderMenuDto> orderMenus;

    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;

    private int totalPrice;

    public OrderQueryDto(Order order) {
        this.orderId = order.getId();
        this.storeName = order.getStore().getStoreName();
        this.customerName = order.getCustomerProfile().getMember().getUsername();
        this.address = new AddressDto(order.getDelivery().getAddress());
        this.orderMenus = order.getOrderMenus().stream()
                .map(orderMenu -> new OrderMenuDto(orderMenu))
                .collect(toList());
        this.orderStatus = order.getOrderStatus();
        this.deliveryStatus = order.getDelivery().getStatus();
        this.totalPrice = order.getTotalPrice();
    }


}
