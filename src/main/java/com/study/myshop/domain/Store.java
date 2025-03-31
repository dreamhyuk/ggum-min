package com.study.myshop.domain;

import com.study.myshop.domain.member.Member;
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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreCategoryMapping> storeCategoryMappings = new ArrayList<>();

    /* 연관관계 편의 메서드 */
    public void addStoreCategoryMapping(StoreCategoryMapping storeCategoryMapping) {
        storeCategoryMappings.add(storeCategoryMapping);
        storeCategoryMapping.setStore(this);
    }

    private Store(String storeName, Address address, Member member, StoreCategoryMapping... storeCategoryMappings) {
        this.storeName = storeName;
        this.address = address;
        this.member = member;
        for (StoreCategoryMapping mapping: storeCategoryMappings) {
            this.addStoreCategoryMapping(mapping);
        }
    }

    /* 생성 메서드 */
    public static Store createStore(String storeName, Address address, Member member, StoreCategoryMapping... storeCategoryMappings) {
        return new Store(storeName, address, member, storeCategoryMappings);
    }
}
