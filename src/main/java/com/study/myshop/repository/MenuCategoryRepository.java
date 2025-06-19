package com.study.myshop.repository;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.category.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    Optional<MenuCategory> findByIdAndStoreId(Long menuCategoryId, Long storeId);

    List<MenuCategory> findAllByStoreId(Long storeId);

}
