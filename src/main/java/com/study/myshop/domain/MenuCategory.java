package com.study.myshop.domain;

import com.study.myshop.domain.category.Category;
import com.study.myshop.domain.menu.Menu;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class MenuCategory {

    @Id @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
