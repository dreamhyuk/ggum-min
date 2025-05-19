package com.study.myshop.dto;

import com.study.myshop.domain.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class OrderMenuDto {

    private String menuName; //메뉴 명
    private int orderPrice; //주문 가격
    private int count;

    public OrderMenuDto(OrderMenu orderMenu) {
        this.menuName = orderMenu.getMenu().getName();
        this.orderPrice = orderMenu.getOrderPrice();
        this.count = orderMenu.getCount();
    }
}
