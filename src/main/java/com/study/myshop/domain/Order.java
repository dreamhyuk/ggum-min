package com.study.myshop.domain;

import com.study.myshop.common.BaseTimeEntity;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.profile.CustomerProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    /**
     * Member에서 CustomerProfile로 바꿨음.
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerProfile customerProfile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.PRIVATE)
    private OrderStatus orderStatus; // [ORDER, CANCEL]

    private int totalPrice;

    public void addMenus(OrderMenu orderMenu) {
        this.orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    public static Order createOrder(CustomerProfile customerProfile, Store store, Delivery delivery, List<OrderMenu> orderMenus) {
        Order order = new Order();
        order.customerProfile = customerProfile;
        order.store = store;
        order.delivery = delivery;
        for (OrderMenu orderMenu: orderMenus) {
            order.addMenus(orderMenu);
        }
        order.setOrderStatus(OrderStatus.WAITING);

        return order;
    }

    /**
     * 주문 상태
     */
    public void accept() {
        if (this.orderStatus != OrderStatus.WAITING) {
            throw new IllegalStateException("이미 처리된 주문입니다.");
        }
        this.orderStatus = OrderStatus.ACCEPTED;
    }
    public void deliveryReady() {
        if (this.orderStatus != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("이미 준비된 주문입니다.");
        }
        this.orderStatus = OrderStatus.DELIVERY_READY;
    }
    public void complete() {
        if (this.orderStatus != OrderStatus.DELIVERY_READY) {
            throw new IllegalStateException("이미 완료된 주문입니다");
        }
        this.orderStatus = OrderStatus.COMPLETED;
    }

    /**
     * 배달 상태
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.DELIVERED) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setOrderStatus(OrderStatus.CANCEL);
    }


    /**
     * 전체 주문가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderMenu orderMenu: orderMenus) {
            totalPrice += orderMenu.getTotalPrice();
        }

        return totalPrice;
    }

}
