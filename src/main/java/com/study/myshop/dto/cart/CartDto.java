package com.study.myshop.dto.cart;

import com.study.myshop.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CartDto {

    private Long cartId;
    private String customerName;
    private int totalPrice;

    private List<CartItemDto> cartItems;


    public CartDto(Cart cart) {
        this.cartId = cart.getId();
        this.customerName = cart.getCustomer().getMember().getUsername();
        this.totalPrice = cart.getTotalPrice();
        this.cartItems = cart.getCartItems().stream()
                .map(cartItem -> new CartItemDto(cartItem))
                .collect(Collectors.toList());
    }


}
