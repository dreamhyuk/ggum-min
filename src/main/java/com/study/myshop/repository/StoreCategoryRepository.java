package com.study.myshop.repository;

import com.study.myshop.domain.category.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {

    Optional<StoreCategory> findByName(String categoryName);
}
