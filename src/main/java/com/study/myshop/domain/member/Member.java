package com.study.myshop.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.member.profile.CustomerProfile;
import com.study.myshop.domain.member.profile.RiderProfile;
import com.study.myshop.domain.member.profile.OwnerProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();

    //역할별 1:1 매핑
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_profile_id")
    private CustomerProfile customerProfile;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rider_profile_id")
    private RiderProfile riderProfile;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_profile_id")
    private OwnerProfile ownerProfile;

    @Enumerated(EnumType.STRING)
    private Role role; //[ROLE_CUSTOMER, ROLE_RIDER, ROLE_STORE_OWNER]

    private String username;
    private String password;
    private String phoneNumber;


    //테스트 생성자
    public Member(Long id, String username, String password, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


    /* 연관관계 메서드 */
    public void addCustomerProfile(CustomerProfile profile) {
        this.customerProfile = profile;
        profile.setMember(this);
    }

    public void addOwnerProfile(OwnerProfile profile) {
        this.ownerProfile = profile;
        profile.setMember(this);
    }

    public void addRiderProfile(RiderProfile profile) {
        this.riderProfile = profile;
        profile.setMember(this);
    }

    /* 생성 메서드 */
    public static Member createCustomer(String username, String password, String phoneNumber, Address address) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.role = Role.ROLE_CUSTOMER;

        CustomerProfile profile = new CustomerProfile(address);
        member.addCustomerProfile(profile);

        return member;
    }

/*    public static Member createCustomer2(CustomerRequestDto request) {
        Member member = new Member();
        member.username = request.getUsername();
        member.password = request.getPassword();
        member.phoneNumber = request.getPhoneNumber();
        member.role = Role.ROLE_CUSTOMER;

        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
        CustomerProfile profile = new CustomerProfile(address);
        member.addCustomerProfile(profile);

        return member;
    }*/

    public static Member createOwner(String username, String password, String phoneNumber, String businessNumber) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.role = Role.ROLE_STORE_OWNER;

        OwnerProfile profile = new OwnerProfile(businessNumber);
        member.addOwnerProfile(profile);

        return member;
    }

/*    public static Member createOwner2(OwnerRequestDto request) {
        Member member = new Member();
        member.username = request.getUsername();
        member.password = request.getPassword();
        member.phoneNumber = request.getPhoneNumber();
        member.role = Role.ROLE_STORE_OWNER;

        OwnerProfile profile = new OwnerProfile(request.getBusinessNumber());
        member.addOwnerProfile(profile);

        return member;
    }*/

    public static Member createRider(String username, String password, String phoneNumber, String vehicleType) {
        Member member = new Member();
        member.username = username;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.role = Role.ROLE_RIDER;

        RiderProfile profile = new RiderProfile(vehicleType);
        member.addRiderProfile(profile);

        return member;
    }

/*    public static Member createRider2(RiderRequestDto request) {
        Member member = new Member();
        member.username = request.getUsername();
        member.password = request.getPassword();
        member.phoneNumber = request.getPhoneNumber();
        member.role = Role.ROLE_RIDER;

        RiderProfile profile = new RiderProfile(request.getVehicleType());
        member.addRiderProfile(profile);

        return member;
    }*/

}
