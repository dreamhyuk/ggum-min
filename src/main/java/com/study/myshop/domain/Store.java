package com.study.myshop.domain;

import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.domain.member.profile.OwnerProfile;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.store.UpdateStoreInfoRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    /**
     * Store는 Owner의 정보만 필요함.
     * 그래서 Member보단 OwnerProfile을 필드로 가져 봤다.
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_profile_id")
    private OwnerProfile ownerProfile;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<StoreCategoryMapping> storeCategoryMappings = new ArrayList<>();

//    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<MenuCategory> menuCategories = new ArrayList<>();


    /* 연관관계 편의 메서드 */
//    public void addMenuCategory(MenuCategory menuCategory) {
//        menuCategories.add(menuCategory);
//        menuCategory.setStore(this);
//    }
    public MenuCategory addMenuCategory(String name) {
        MenuCategory menuCategory = MenuCategory.createCategory(name, this);
        menuCategories.add(menuCategory);

        return menuCategory;
    }


    public void addStoreCategoryMapping(StoreCategoryMapping storeCategoryMapping) {
        if (this.storeCategoryMappings.size() >= 2) {
            throw new IllegalStateException("Max 2category!!");
        }
        storeCategoryMappings.add(storeCategoryMapping);
        storeCategoryMapping.setStore(this);
    }

//    public void addMenu(Menu menu) {
//        menus.add(menu);
//        menu.setStore(this);
//    }
//
//    public void removeMenu(Menu menu) {
//        if (!menus.contains(menu)) {
//            throw new IllegalArgumentException("해당 메뉴가 존재하지 않습니다.");
//        }
//
//        menus.remove(menu);
//        menu.setStore(null); // 양방향 연관관계 정리
//    }

    /* 생성 메서드 */
    public static Store createStore(String storeName, Address address, OwnerProfile ownerProfile, StoreCategoryMapping... storeCategoryMappings) {
        return new Store(storeName, address, ownerProfile, storeCategoryMappings);
    }

    public static Store createStore(String storeName, Address address, OwnerProfile ownerProfile, List<StoreCategoryMapping> mappings) {
        return new Store(storeName, address, ownerProfile, mappings);
    }

    //파라미터로 StoreCategory 를 직접 받아 생성
    public static Store create(String storeName, Address address, OwnerProfile ownerProfile, List<StoreCategory> categories) {
        Store store = new Store();
        store.storeName = storeName;
        store.address = address;
        store.ownerProfile = ownerProfile;
        for (StoreCategory category: categories) {
            store.addStoreCategoryMapping(StoreCategoryMapping.createMapping(category));
        }
        return store;
    }


    /**
     * varargs 생성 방식
     */
    private Store(String storeName, Address address, OwnerProfile ownerProfile, StoreCategoryMapping... storeCategoryMappings) {
        this.storeName = storeName;
        this.address = address;
        this.ownerProfile = ownerProfile;
        for (StoreCategoryMapping mapping: storeCategoryMappings) {
            this.addStoreCategoryMapping(mapping);
        }
    }

    /**
     * List<StoreCategoryMapping> 생성 방식
     * 이게 더 괜찮을 거 같음.
     */
    private Store(String storeName, Address address, OwnerProfile ownerProfile, List<StoreCategoryMapping> mappings) {
        this.storeName = storeName;
        this.address = address;
        this.ownerProfile = ownerProfile;
        for (StoreCategoryMapping mapping: mappings) {
            this.addStoreCategoryMapping(mapping);
        }
    }

    /* 비즈니스 로직 */

    /**
     * 기본 정보 수정
     */
    public void updateInfo(UpdateStoreInfoRequest request) {
        this.storeName = request.getStoreName();
        this.address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
    }

    /**
     * 메뉴 리스트 추가
     */
//    public void addMenus(List<Menu> menuList) {
//        for(Menu menu: menuList) {
//            addMenu(menu);
//        }
//    }

    /**
     * 메뉴 리스트 삭제
     */
//    public void removeMenus(List<Menu> menuList) {
//        for(Menu menu: menuList) {
//            removeMenu(menu);
//        }
//    }

}
