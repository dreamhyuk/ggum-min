package com.study.myshop.api;

import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.Store;
import com.study.myshop.dto.AddMenuCategoryRequest;
import com.study.myshop.dto.AddMenuCategoryResponse;
import com.study.myshop.repository.MenuCategoryRepository;
import com.study.myshop.repository.StoreRepository;
import com.study.myshop.service.MenuCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuCategoryApiController {

    private final MenuCategoryService menuCategoryService;


    @PostMapping("/stores/{storeId}/menu-categories")
    public ResponseEntity<ApiResponse<AddMenuCategoryResponse>> createMenuCategory(@PathVariable("storeId") Long storeId,
                                                                                   @RequestBody @Valid AddMenuCategoryRequest request) {

        Long id = menuCategoryService.saveMenuCategory(storeId, request.getMenuCategoryName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(200, "카테고리 생성 성공", new AddMenuCategoryResponse(id)));
    }

}
