package com.study.myshop.domain.category;

import com.study.myshop.domain.StoreCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

//    @ManyToMany
//    @JoinTable(name = "category_menu",
//            joinColumns = @JoinColumn(name = "category_id"),
//            inverseJoinColumns = @JoinColumn(name = "food_id"))
//    private List<Food> foods = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<StoreCategory> storeCategories = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @Setter(AccessLevel.PRIVATE)
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type; //[STORE, MENU]

    /* 연관관계 편의 메서드 */
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

    /* 생성 메서드 */
    public static Category createStoreCategory(String name, Category parent) {
        Category category = new Category();
        category.name = name;
        category.type = CategoryType.STORE;
        if (parent != null) {
            category.parent = parent;
            parent.getChild().add(category);
        }

        return category;
    }

    public static Category createMenuCategory(String name, Category parent) {
        Category category = new Category();
        category.name  = name;
        category.type = CategoryType.MENU;
        if (parent != null) {
            category.parent = parent;
            parent.getChild().add(category);
        }

        return category;
    }
}
