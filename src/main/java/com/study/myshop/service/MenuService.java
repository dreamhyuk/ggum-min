package com.study.myshop.service;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.menu.AddMenuRequest;
import com.study.myshop.repository.MenuCategoryRepository;
import com.study.myshop.repository.MenuRepository;
import com.study.myshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    /* 등록 */
    @Transactional
    public Long saveMenu(Long storeId, Long menuCategoryId, AddMenuRequest request, Long userId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음."));

        Long ownerId = store.getOwnerProfile().getMember().getId();
        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        MenuCategory menuCategory = menuCategoryRepository.findByIdAndStoreId(menuCategoryId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴 카테고리 없음."));

        Menu menu = Menu.createMenu(request.getMenuName(), request.getPrice(), menuCategory, store);
        menuRepository.save(menu);

        return menu.getId();
    }

    /* 수정 */
    @Transactional
    public void updateMenu(Long storeId, Long menuCategoryId, Long menuId, AddMenuRequest request, Long userId) {

        Menu findMenu = menuRepository.findByIdAndStoreIdAndMenuCategoryId(menuId, storeId, menuCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴 없음."));

        Long ownerId = findMenu.getStore().getOwnerProfile().getMember().getId();
        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

//        findMenu.updateMenu(request.getMenuName(), request.getPrice());
        if (request.getMenuName() != null) {
            findMenu.changeName(request.getMenuName());
        }
        if (request.getPrice() != null) {
            findMenu.changPrice(request.getPrice());
        }

    }

    /* 삭제 */
    @Transactional
    public Long removeMenu(Long storeId, Long menuCategoryId, Long menuId, Long userId) {

        Menu findMenu = menuRepository.findByIdAndStoreIdAndMenuCategoryId(menuId, storeId, menuCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴 없음"));

        Long ownerId = findMenu.getStore().getOwnerProfile().getMember().getId();
        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        menuRepository.delete(findMenu);

        return findMenu.getId();
    }



    public Menu findOne(Long storeId, Long menuCategoryId, Long menuId) {
        return menuRepository.findByIdAndStoreIdAndMenuCategoryId(menuId, storeId, menuCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음."));
    }

}
