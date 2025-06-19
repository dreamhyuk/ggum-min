package com.study.myshop.service;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.repository.MenuCategoryRepository;
import com.study.myshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Long saveMenuCategory(Long storeId, String menuCategoryName, Long userId) {

        Store findStore = storeRepository.findWithMemberById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음."));

        Long ownerId = findStore.getOwnerProfile().getMember().getId();
        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        MenuCategory category = MenuCategory.createCategory(menuCategoryName, findStore);
        menuCategoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public void updateMenuCategory(Long storeId, Long menuCategoryId, String menuCategoryName, Long userId) {

        Store findStore = storeRepository.findWithMemberById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음."));

        Long ownerId = findStore.getOwnerProfile().getMember().getId();
        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        MenuCategory menuCategory = menuCategoryRepository.findByIdAndStoreId(menuCategoryId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게의 카테고리가 아닙니다."));

        menuCategory.update(menuCategoryName);
    }

    @Transactional
    public Long removeMenuCategory(Long storeId, Long menuCategoryId, Long userId) {

        Store findStore = storeRepository.findWithMemberById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음."));

        Long ownerId = findStore.getOwnerProfile().getMember().getId();
        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        MenuCategory menuCategory = menuCategoryRepository.findByIdAndStoreId(menuCategoryId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게의 카테고리가 아닙니다."));

        menuCategoryRepository.delete(menuCategory);

        return menuCategory.getId();
    }


    public List<MenuCategory> findAll(Long storeId) {
        return menuCategoryRepository.findAllByStoreId(storeId);
    }

    public MenuCategory findOne(Long storeId, Long menuCategoryId) {
        return menuCategoryRepository.findByIdAndStoreId(menuCategoryId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴 카테고리 없음."));
    }

}
