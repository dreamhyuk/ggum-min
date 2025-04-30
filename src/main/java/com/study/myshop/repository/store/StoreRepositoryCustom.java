package com.study.myshop.repository.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.myshop.domain.Store;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepositoryCustom {

    List<Store> findByStoreName(String storeName);

    List<Store> findByCategoryId(Long categoryId);

    Optional<Store> findStoreById(Long storeId);

    Optional<Store> findWithMemberById(Long storeId);

    Store findStoreWithCategories(Long storeId);

}
