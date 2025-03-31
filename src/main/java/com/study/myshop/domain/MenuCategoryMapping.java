package com.study.myshop.domain;

import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCategoryMapping {

    @Id @GeneratedValue
    @Column(name = "menu_category_mapping_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    @Setter
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;


    public MenuCategoryMapping(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
    }

    public static MenuCategoryMapping createMapping(MenuCategory menuCategory) {
        return new MenuCategoryMapping(menuCategory);
    }

}
