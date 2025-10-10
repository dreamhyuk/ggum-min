package com.study.myshop.domain;

import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.domain.member.profile.OwnerProfile;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.store.UpdateStoreInfoRequest;
import jakarta.annotation.Nullable;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String storeName;

    private String imageUrl;

    @Embedded
    private Address address;


    /**
     * Store는 Owner의 정보만 필요함.
     * 그래서 Member보단 OwnerProfile을 필드로 가져 봤다.
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_profile_id")
    private OwnerProfile ownerProfile;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<StoreCategoryMapping> storeCategoryMappings = new ArrayList<>();

//    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<MenuCategory> menuCategories = new ArrayList<>();

    public Store(String storeName, String imageUrl, Address address, OwnerProfile ownerProfile) {
        this.storeName = storeName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.ownerProfile = ownerProfile;
    }


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


    /**
     * 생성 메서드
     * ! StoreCategoryMapping 리스트는 생성 시점에 완전히 구성된 상태여야 한다.
     * List<StoreCategoryMapping>은 Store와 StoreCategory 사이의 중간 매핑 엔티티이다.
     * 그렇다면 이건 Store를 만든 이후에 매핑을 추가해주는 방법이 더 알맞지 않을까?
     */
    /*  */
    public static Store createStore(String storeName, Address address, OwnerProfile ownerProfile, StoreCategoryMapping... storeCategoryMappings) {
        return new Store(storeName, address, ownerProfile, storeCategoryMappings);
    }

    /* List로 받기 */
    public static Store createStore(String storeName, Address address, OwnerProfile ownerProfile, List<StoreCategoryMapping> mappings) {
        return new Store(storeName, address, ownerProfile, mappings);
    }

    /** Store 먼저 생성 후 카테고리 나중에 add */
    public static Store create(String storeName, @Nullable String imageUrl, Address address, OwnerProfile ownerProfile) {
        return new Store(storeName, imageUrl, address, ownerProfile);
    }

    //단건 추가
    public void addCategoryMapping(StoreCategoryMapping mapping) {
        this.storeCategoryMappings.add(mapping);
        mapping.setStore(this);
    }

    //다건 추가 (편의용)
    public void addCategoryMappings(List<StoreCategoryMapping> mappings) {
        mappings.forEach(this::addCategoryMapping);
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
    public void updateInfo(UpdateStoreInfoRequest request, List<StoreCategoryMapping> mappings) {
        this.storeName = request.getStoreName();
//        this.address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
        this.address = request.getAddressDto().toEntity();

        this.storeCategoryMappings.clear(); // 리스트에서 제거

        for(StoreCategoryMapping mapping: mappings) {
            this.addStoreCategoryMapping(mapping);
        }
    }

    /**
     * 메뉴 리스트 추가
     */
/*    public void addMenus(List<Menu> menuList) {
        for(Menu menu: menuList) {
            addMenu(menu);
        }
    }*/

    /**
     * 메뉴 리스트 삭제
     */
/*    public void removeMenus(List<Menu> menuList) {
        for(Menu menu: menuList) {
            removeMenu(menu);
        }
    }*/

}
