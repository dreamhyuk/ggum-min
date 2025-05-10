package com.study.myshop.domain.member.profile;

import com.study.myshop.domain.Address;
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

    @Embedded
    private Address address;

    public CustomerProfile(Address address) {
        this.address = address;
    }
}
