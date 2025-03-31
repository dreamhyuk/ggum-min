package com.study.myshop.domain.category;

import com.study.myshop.domain.MenuCategoryMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCategory {

    @Id @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    private String name; //메인, 사이드, 음료 등..

    public MenuCategory(String name) {
        this.name = name;
    }

    public static MenuCategory createCategory(String name) {
        return new MenuCategory(name);
    }
}
