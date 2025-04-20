package com.study.myshop.repository.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.myshop.domain.QStoreCategoryMapping;
import com.study.myshop.domain.Store;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.study.myshop.domain.QStore.store;
import static com.study.myshop.domain.QStoreCategoryMapping.storeCategoryMapping;
import static com.study.myshop.domain.category.QStoreCategory.storeCategory;
import static com.study.myshop.domain.member.profile.QOwnerProfile.ownerProfile;

@Repository
public class StoreRepositoryImpl implements StoreRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory query;
    
    public StoreRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Store> findByStoreName(String storeName) {
        return query
                .select(store)
                .from(store)
                .join(store.ownerProfile, ownerProfile).fetchJoin()
                .leftJoin(store.storeCategoryMappings, storeCategoryMapping).fetchJoin()
                .leftJoin(storeCategoryMapping.storeCategory, storeCategory).fetchJoin()
                .where(nameLike(storeName))
                .distinct()
                .fetch();
    }

    @Override
    public List<Store> findByCategoryId(Long categoryId) {
        return query
                .select(store)
                .from(store)
                .join(store.ownerProfile, ownerProfile).fetchJoin()
                .join(store.storeCategoryMappings, storeCategoryMapping).fetchJoin()
                .join(storeCategoryMapping.storeCategory, storeCategory).fetchJoin()
                .where(categoryEq(categoryId))
                .distinct()
                .fetch();
    }

    private BooleanExpression nameLike(String storeName) {
        if (!StringUtils.hasText(storeName)) {
            return null;
        }
//        return store.storeName.like(storeName);
        return store.storeName.contains(storeName); //부분 검색 적용
    }

    private BooleanExpression categoryEq(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return storeCategory.id.eq(categoryId);
    }

}
