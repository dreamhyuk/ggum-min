package com.study.myshop.domain.category;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.menu.Menu;
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
public class MenuCategory {

    @Id @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    private String name; //메인, 사이드, 음료 등..

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "menuCategory")
    @BatchSize(size = 10)
    private List<Menu> menus = new ArrayList<>();

    public void setStore(Store store) {
        this.store = store;
    }

    public static MenuCategory createCategory(String name, Store store) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.name = name;
        menuCategory.store = store;
        return menuCategory;
    }

    public void update(String menuCategoryName) {
        this.name = menuCategoryName;
    }


}
