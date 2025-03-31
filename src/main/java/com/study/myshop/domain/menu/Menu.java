package com.study.myshop.domain.menu;

import com.study.myshop.domain.MenuCategoryMapping;
import com.study.myshop.domain.OrderMenu;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

//    private int stockQuantity; //(배달 어플에서) 재고 수량이 필요한가? 필요 없을 거 같다..
//    private boolean isRecommended; //대표메뉴 여부

    @OneToMany(mappedBy = "menu")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuCategoryMapping> menuCategoryMappings = new ArrayList<>();

    /* 연관관계 편의 메서드 */
    public void addMenuCategoryMapping(MenuCategoryMapping menuCategoryMapping) {
        menuCategoryMappings.add(menuCategoryMapping);
        menuCategoryMapping.setMenu(this);
    }

    private Menu(String name, int price, MenuCategoryMapping menuCategoryMapping) {
        this.name = name;
        this.price = price;
        this.addMenuCategoryMapping(menuCategoryMapping);
    }

    /* 생성 메서드 */
    public static Menu addMenu(String name, int price, MenuCategoryMapping menuCategoryMapping) {
        return new Menu(name, price, menuCategoryMapping);
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
