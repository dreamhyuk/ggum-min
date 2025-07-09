package com.study.myshop.domain;

import com.study.myshop.domain.member.profile.CustomerProfile;
import com.study.myshop.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerProfile customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    public static Cart createCart() {
        Cart cart = new Cart();
        return cart;
    }

    public void setCustomer(CustomerProfile customer) {
        this.customer = customer;
        customer.setCart(this);
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public void addMenu(Menu menu, int count) {
        CartItem existing = findCartItem(menu);
        if (existing != null) {
            existing.addCount(count);
        } else {
            CartItem newItem = new CartItem(this, menu, menu.getPrice(), count);
            addCartItem(newItem);
        }
    }

    private CartItem findCartItem(Menu menu) {
        return cartItems.stream()
                .filter(ci -> ci.getMenu().equals(menu))
                .findFirst()
                .orElse(null);
    }

    /**
     * 전체 주문가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(CartItem cartItem: cartItems) {
            totalPrice += cartItem.getTotalPrice();
        }

        return totalPrice;
    }

}
