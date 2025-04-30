package com.study.myshop.repository.store;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.myshop.domain.QStoreCategoryMapping;
import com.study.myshop.domain.Store;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.StoreCategoryDto;
import com.study.myshop.dto.owner.OwnerDto;
import com.study.myshop.dto.store.StoreWithCategoriesDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.study.myshop.domain.QStore.store;
import static com.study.myshop.domain.QStoreCategoryMapping.storeCategoryMapping;
import static com.study.myshop.domain.category.QMenuCategory.menuCategory;
import static com.study.myshop.domain.category.QStoreCategory.storeCategory;
import static com.study.myshop.domain.member.QMember.member;
import static com.study.myshop.domain.member.profile.QOwnerProfile.ownerProfile;
import static com.study.myshop.domain.menu.QMenu.menu;

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
//                .join(store.ownerProfile, ownerProfile).fetchJoin() //가게 목록 조회에서 owner를 join할 필요가 없다.
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


    public Optional<Store> findStoreById(Long storeId) {
        Store result = query
                .select(store)
                .from(store)
                .join(store.ownerProfile, ownerProfile).fetchJoin()
                .join(ownerProfile.member, member).fetchJoin()
                .leftJoin(store.storeCategoryMappings, storeCategoryMapping).fetchJoin()
                .leftJoin(storeCategoryMapping.storeCategory, storeCategory).fetchJoin()
//                .leftJoin(store.menuCategories, menuCategory).fetchJoin() //MultipleBagFetchException 발생으로 제거.
//                .leftJoin(menuCategory.menus, menu).fetchJoin() //MultipleBagFetchException 발생으로 제거.
                .where(store.id.eq(storeId))
                .fetchOne(); //단건 조회

        return Optional.ofNullable(result);
    }

    /**
     * Store + StoreCategory
     * fetch join으로 조회
     */
    //Store 엔티티를 반환
    public Store findStoreWithCategories(Long storeId) {
        return query
                .select(store)
                .from(store)
                .join(store.ownerProfile, ownerProfile).fetchJoin()
//                .join(ownerProfile.member, member).fetchJoin() //굳이 member까지 join할 필욘 없을 거 같음.
                .leftJoin(store.storeCategoryMappings, storeCategoryMapping).fetchJoin()
                .leftJoin(storeCategoryMapping.storeCategory, storeCategory).fetchJoin()
                .where(store.id.eq(storeId))
                .fetchOne();
    }

    //StoreDto 바로 반환 <- 오히려 복잡해질 수 있다.
    //그리고 contructor projection과 fetch join은 기술 상 가능하지만, dto는 영속성과 무관하므로 의미가 없다.
    public StoreWithCategoriesDto findStoreWithCategoriesDto(Long storeId) {
        return query
                .select(Projections.constructor(StoreWithCategoriesDto.class,
                        store.id,
                        store.storeName,
                        Projections.constructor(AddressDto.class,
                                store.address.city,
                                store.address.street,
                                store.address.zipcode
                        ),
                        Projections.constructor(OwnerDto.class,
                                ownerProfile.id,
                                member.username
                        )
                ))
                .from(store)
                .join(store.ownerProfile, ownerProfile).fetchJoin()
                .join(ownerProfile.member, member).fetchJoin()
                .where(store.id.eq(storeId))
                .fetchOne();
    }



    public Optional<Store> findWithMemberById(Long storeId) {
        Store result = query
                .select(store)
                .from(store)
                .join(store.ownerProfile, ownerProfile).fetchJoin()
                .join(ownerProfile.member, member).fetchJoin()
                .where(store.id.eq(storeId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
