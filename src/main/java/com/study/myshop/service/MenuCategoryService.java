package com.study.myshop.service;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.dto.AddMenuCategoryRequest;
import com.study.myshop.repository.MenuCategoryRepository;
import com.study.myshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Long saveMenuCategory(Long storeId, String menuCategoryName) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        MenuCategory category = MenuCategory.createCategory(menuCategoryName, store);
        menuCategoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public void updateMenuCategory(Long storeId, Long menuCategoryId, String menuCategoryName) {

        MenuCategory menuCategory = menuCategoryRepository.findByIdAndStoreId(menuCategoryId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게의 카테고리가 아닙니다."));

        menuCategory.update(menuCategoryName);
    }

    @Transactional
    public Long removeMenuCategory(Long storeId, Long menuCategoryId) {

        MenuCategory menuCategory = menuCategoryRepository.findByIdAndStoreId(menuCategoryId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게 카테고리가 아닙니다."));

        menuCategoryRepository.delete(menuCategory);

        return menuCategory.getId();
    }

}
