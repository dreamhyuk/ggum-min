package com.study.myshop.repository;

import com.study.myshop.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

//    Optional<Menu> findByIdAndStoreId(Long menuId, Long storeId);

    Optional<Menu> findByIdAndMenuCategoryId(Long menuId, Long menuCategoryId);

    List<Menu> findAllByMenuCategoryId(Long menuCategoryId);
}
