package com.study.myshop.controller;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.dto.store.CreateStoreRequest;
import com.study.myshop.repository.StoreCategoryRepository;
import com.study.myshop.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreService storeService;

    @GetMapping("owner/stores/new")
    public String createStore(Model model) {
        List<StoreCategory> categories = storeCategoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "store/createStoreForm";
    }

    @GetMapping("/stores/{storeId}")
    public String storeDetail() {
        return "store/storeDetail";
    }

    @GetMapping("/owner/stores/my-stores")
    public String getMyStores() {
        return "store/myStores";
    }

    @GetMapping("/owner/stores/{storeId}/management")
    public String storeManagement(@PathVariable Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "store/storeManagement";
    }

    @GetMapping("/owner/stores/{storeId}/menus/new")
    public String addMenus(@PathVariable("storeId") Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "store/addMenu";
    }

    @GetMapping("/owner/stores/{storeId}/menus/update")
    public String updateMenus() {
        return "store/updateMenu";
    }

    @GetMapping("/owner/stores/{storeId}/info/update")
    public String updateInfo(@PathVariable Long storeId, Model model) {
        Store store = storeService.findOne(storeId);
        List<StoreCategory> categories = storeCategoryRepository.findAll();

        model.addAttribute("store", new CreateStoreRequest(store));
        model.addAttribute("categories", categories);
        return "store/updateInfo";
    }

    @GetMapping("/owner/stores/{storeId}/menu-categories/new")
    public String createMenuCategory(@PathVariable Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "store/createMenuCategories";
    }

    @GetMapping("/owner/stores/{storeId}/view")
    public String viewStore(@PathVariable Long storeId, Model model) {
        model.addAttribute("storeId", storeId);

        return "store/ownerStoreDetail";
    }
}
