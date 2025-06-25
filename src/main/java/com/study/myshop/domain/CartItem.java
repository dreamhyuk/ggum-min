package com.study.myshop.domain;

import com.study.myshop.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    @Setter
    private Cart cart;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int price;

    private int count;

    public static CartItem createCartItem(Menu menu, int menuPrice, int count) {
        CartItem cartItem = new CartItem();
        cartItem.menu = menu;
        cartItem.price = menuPrice;
        cartItem.count = count;
        return cartItem;
    }

    public CartItem(Cart cart, Menu menu, int menuPrice, int count) {
        this.cart = cart;
        this.menu = menu;
        this.price = menuPrice;
        this.count = count;
    }

    public void addCount(int amount) {
        this.count += amount;
    }

    public void changeCount(int newCount) {
        this.count = newCount;
    }

    /**
     * 장바구니 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getPrice() * getCount();
    }

}
