package com.study.myshop.domain.member;

import com.study.myshop.domain.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class CustomerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_profile_id")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "customerProfile")
    private Member member;
}
