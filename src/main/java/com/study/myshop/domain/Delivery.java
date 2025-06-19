package com.study.myshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, PICKED_UP, DELIVERED

    public static Delivery createDelivery(Address address) {
        Delivery delivery = new Delivery();
        delivery.address = address;
        delivery.status = DeliveryStatus.WAITING;
        return delivery;
    }

    public void changeStatus(DeliveryStatus status) {
        this.status = status;
    }

}
