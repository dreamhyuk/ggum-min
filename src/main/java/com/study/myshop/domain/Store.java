package com.study.myshop.domain;

import com.study.myshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Store {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String storeName;

    @Embedded
    private Address address;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "store")
    private List<StoreCategory> storeCategories = new ArrayList<>();
}
