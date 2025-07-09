package com.study.myshop.domain;

import com.study.myshop.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    @Setter
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int orderPrice; //주문 당시 가격. 음식 가격은 바뀔 수 있다.
    private int count; //주문 수량

    @Builder
    protected OrderMenu(Menu menu, int orderPrice, int count) {
        this.menu = menu;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    /* 생성 메서드 */
    public static OrderMenu createOrderMenu(Menu menu, int orderPrice, int count) {
        OrderMenu orderMenu = OrderMenu.builder()
                .menu(menu)
                .orderPrice(orderPrice)
                .count(count)
                .build();
        return orderMenu;
    }

    /* 비즈니스 로직 */
//    public void cancel() {
//        getMenu().addStock(count);
//    }

    /* 조회 로직 */
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
