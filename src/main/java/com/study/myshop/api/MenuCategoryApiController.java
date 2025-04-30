package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.dto.menu_category.AddMenuCategoryRequest;
import com.study.myshop.dto.menu_category.AddMenuCategoryResponse;
import com.study.myshop.dto.menu_category.UpdateMenuCategoryResponse;
import com.study.myshop.service.MenuCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuCategoryApiController {

    private final MenuCategoryService menuCategoryService;


    @PostMapping("/stores/{storeId}/menu-categories")
    public ResponseEntity<ApiResponse<AddMenuCategoryResponse>> createMenuCategory(
            @PathVariable("storeId") Long storeId,
            @RequestBody @Valid AddMenuCategoryRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();

        Long id = menuCategoryService.saveMenuCategory(storeId, request.getMenuCategoryName(), userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(200, "카테고리 생성 성공", new AddMenuCategoryResponse(id)));
    }

    @PatchMapping("/stores/{storeId}/menu-categories/{menuCategoryId}")
    public ResponseEntity<ApiResponse<UpdateMenuCategoryResponse>> updateMenuCategory(
            @PathVariable("storeId") Long storeId,
            @PathVariable("menuCategoryId") Long menuCategoryId,
            @RequestBody @Valid AddMenuCategoryRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();

        menuCategoryService.updateMenuCategory(storeId, menuCategoryId, request.getMenuCategoryName(), userId);

        MenuCategory findMenuCategory = menuCategoryService.findOne(storeId, menuCategoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("메뉴 카테고리 수정", new UpdateMenuCategoryResponse(findMenuCategory)));
    }

    @DeleteMapping("/stores/{storeId}/menu-categories/{menuCategoryId}")
    public ResponseEntity<ApiResponse<Void>> removeMenuCategory(
            @PathVariable("storeId") Long storeId,
            @PathVariable("menuCategoryId") Long menuCategoryId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();

        //삭제 응답으로 id를 보내줘야 하나?
        Long id = menuCategoryService.removeMenuCategory(storeId, menuCategoryId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("메뉴 카테고리 삭제"));
    }

}
