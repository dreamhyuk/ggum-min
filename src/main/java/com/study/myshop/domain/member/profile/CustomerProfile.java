package com.study.myshop.domain.member.profile;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

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

    @Embedded
    private Address address;

    public CustomerProfile(Address address) {
        this.address = address;
    }
}
