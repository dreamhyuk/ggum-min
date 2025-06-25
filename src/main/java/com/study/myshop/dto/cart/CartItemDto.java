package com.study.myshop.dto.cart;

import com.study.myshop.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {

    private String menuName;
    private int price;
    private int count;

    public CartItemDto(CartItem cartItem) {
        this.menuName = cartItem.getMenu().getName();
        this.price = cartItem.getMenu().getPrice();
        this.count = cartItem.getCount();
    }
}
