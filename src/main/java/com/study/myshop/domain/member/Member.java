package com.study.myshop.domain.member;

import com.study.myshop.domain.Order;
import com.study.myshop.domain.Role;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role; //[ROLE_CUSTOMER, ROLE_DELIVERY_PERSON, ROLE_STORE_OWNER]

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //역할별 1:1 매핑
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_profile_id")
    private CustomerProfile customerProfile;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_profile_id")
    private DeliveryProfile deliveryProfile;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_profile_id")
    private OwnerProfile ownerProfile;

    /* 연관관계 메서드 */
    public void addCustomerProfile(CustomerProfile profile) {
        this.customerProfile = profile;
        profile.setMember(this);
    }

    public void addDeliveryProfile(DeliveryProfile profile) {
        this.deliveryProfile = profile;
        profile.setMember(this);
    }

    public void addOwnerProfile(OwnerProfile profile) {
        this.ownerProfile = profile;
        profile.setMember(this);
    }

    /* 생성 메서드 */
    public static Member createCustomer(String username, String password, String phoneNumber) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.role = Role.ROLE_CUSTOMER;

        CustomerProfile profile = new CustomerProfile();
        member.addCustomerProfile(profile);

        return member;
    }

    public static Member createDeliveryPerson(String username, String password, String phoneNumber) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.role = Role.ROLE_DELIVERY_PERSON;

        DeliveryProfile profile = new DeliveryProfile();
        member.addDeliveryProfile(profile);

        return member;
    }

    public static Member createOwner(String username, String password, String phoneNumber) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.role = Role.ROLE_STORE_OWNER;

        OwnerProfile profile = new OwnerProfile();
        member.addOwnerProfile(profile);

        return member;
    }

}
