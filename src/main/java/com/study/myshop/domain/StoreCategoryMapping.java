package com.study.myshop.domain;

import com.study.myshop.domain.category.StoreCategory;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategoryMapping {

    @Id @GeneratedValue
    @Column(name = "store_category_mapping_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    @Setter
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_category_id")
    private StoreCategory storeCategory;

//    private boolean isPrimary; //메인 카테고리가 있다면, true면 메인, false면 서브 카테고리


    public StoreCategoryMapping(StoreCategory storeCategory) {
        this.storeCategory = storeCategory;
    }

    public static StoreCategoryMapping createMapping(StoreCategory storeCategory) {
        StoreCategoryMapping mapping = new StoreCategoryMapping(storeCategory);

        return mapping;
    }

}
