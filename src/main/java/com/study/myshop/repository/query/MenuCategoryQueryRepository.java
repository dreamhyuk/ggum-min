package com.study.myshop.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.dto.MenuCategoryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.myshop.domain.category.QMenuCategory.menuCategory;

@Repository
public class MenuCategoryQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public MenuCategoryQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    public List<MenuCategory> findMenuCategoriesByStoreId(Long storeId) {
        return query
                .select(menuCategory)
                .from(menuCategory)
                .where(menuCategory.store.id.eq(storeId))
                .fetch();
    }


    public List<MenuCategoryDto> findMenuCategoryDtosByStoreId(Long storeId) {
        return query
                .select(Projections.constructor(MenuCategoryDto.class,
                        menuCategory.id,
                        menuCategory.name
                ))
                .from(menuCategory)
                .where(menuCategory.store.id.eq(storeId))
                .fetch();
    }
}
