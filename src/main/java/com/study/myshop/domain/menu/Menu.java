package com.study.myshop.domain.menu;

import com.study.myshop.domain.OrderMenu;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.category.MenuCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    private String name;
    private int price;
    private int quantity;

//    private int stockQuantity; //(배달 어플에서) 재고 수량이 필요한가? 필요 없을 거 같다.
//    private boolean isRecommended; //대표메뉴 여부

    @OneToMany(mappedBy = "menu")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    //메뉴가 굳이 여러 카테고리에 포함될 필요가 있나? (중간 테이블이 없어도 될 듯)
//    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
//    private List<MenuCategoryMapping> menuCategoryMappings = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;


    /* 연관관계 편의 메서드 */
    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
        menuCategory.getMenus().add(this);
    }

//    public void setStore(Store store) {
//        this.store = store;
//    }

    /* 생성 메서드 */
    public static Menu createMenu(String name, int price, MenuCategory menuCategory) {
        Menu menu = new Menu();
        menu.name = name;
        menu.price = price;
        menu.menuCategory = menuCategory;

        return menu;
    }


    public void updateMenu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    //update를 PATCH로 하기 위해 필드 변경 메서드를 개별적으로 나눠봤다.
    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    /* 비즈니스 로직 */
//    /**
//     * stock 증가
//     */
//    public void addStock(int quantity) {
//        this.stockQuantity += quantity;
//    }
//
//    /**
//     * stock 감소
//     */
//    public void removeStock(int quantity) {
//        int restStock = this.stockQuantity - quantity;
//        if (restStock < 0) {
//            throw new NotEnoughStockException("수량이 부족합니다..");
//        }
//        this.stockQuantity = restStock;
//    }
}
