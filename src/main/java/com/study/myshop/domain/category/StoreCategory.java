package com.study.myshop.domain.category;

import com.study.myshop.domain.StoreCategoryMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_category_id")
    private Long id;

    private String name; //한식, 중식, 치킨 등...

    /**
     * 카테고리가 계층 구조 (ex. 의류 -> 상의 -> 반팔)라면
     * private Category parent;
     * private List<Category> child = new ArrayList<>(); 처럼 참조가 이뤄져야 하지만
     * 지금 카테고리는 계층 구조가 아니기 때문에 없어도 된다.
     *
     * private int depth; 필드로 카테고리의 계층적 깊이도 저장 가능.
     * ex.   <depth 1>       (의류  -  음식  -  전자제품)
     *       <depth 2>       (상의, 하의  - 한식, 중식  -  컴퓨터, 핸드폰)
     */
    @OneToMany(mappedBy = "storeCategory", cascade = CascadeType.ALL)
    private List<StoreCategoryMapping> storeCategoryMappings = new ArrayList<>();

    public StoreCategory(String name) {
        this.name = name;
    }


    public static StoreCategory createCategory(String name) {
        StoreCategory storeCategory = new StoreCategory(name);
        return storeCategory;
    }

}
