package com.study.myshop.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class DeliveryProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_profile_id")
    private Long id;

    private String vehicleType;
    private boolean isAvailable;

    @OneToOne(mappedBy = "deliveryProfile")
    private Member member;

}
