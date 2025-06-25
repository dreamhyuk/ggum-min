package com.study.myshop.domain.member.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Cart;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_profile_id")
    private Long id;

    @OneToOne(mappedBy = "customerProfile")
    private Member member;

    @OneToMany(mappedBy = "customerProfile")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @Embedded
    private Address address;

    public CustomerProfile(Address address) {
        this.address = address;
    }
}
