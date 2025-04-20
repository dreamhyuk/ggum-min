package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.menu.AddMenuRequest;
import com.study.myshop.dto.menu.AddMenuResponse;
import com.study.myshop.dto.menu.DeleteMenuResponse;
import com.study.myshop.dto.menu.UpdateMenuResponse;
import com.study.myshop.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuApiController {

    private final MenuService menuService;

    /* 등록 */
    @PostMapping("/stores/{storeId}/menu-categories/{menuCategoryId}/menus")
    public ResponseEntity<ApiResponse<AddMenuResponse>> addMenu(@PathVariable("storeId") Long storeId,
                                                                @PathVariable("menuCategoryId") Long menuCategoryId,
                                                                @RequestBody @Valid AddMenuRequest request,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();

        Long id = menuService.saveMenu(storeId, menuCategoryId, request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "메뉴 생성", new AddMenuResponse(id)));
    }

    /* 수정 */
    @PatchMapping("/stores/{storeId}/menu-categories/{menuCategoryId}/menus/{menuId}")
    public ResponseEntity<ApiResponse<UpdateMenuResponse>> updateMenu(@PathVariable("storeId") Long storeId,
                                                                      @PathVariable("menuCategoryId") Long menuCategoryId,
                                                                      @PathVariable("menuId") Long menuId,
                                                                      @RequestBody @Valid AddMenuRequest request,
                                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();

        menuService.updateMenu(storeId, menuCategoryId, menuId, request, userId);
        Menu findMenu = menuService.findOne(storeId, menuCategoryId, menuId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(200, "메뉴 수정 완료", new UpdateMenuResponse(findMenu)));
    }

    /* 삭제 */
    @DeleteMapping("/stores/{storeId}/menu-categories/{menuCategoryId}/menus/{menuId}")
    public ResponseEntity<ApiResponse<Void>> removeMenu(@PathVariable("storeId") Long storeId,
                                                        @PathVariable("menuCategoryId") Long menuCategoryId,
                                                        @PathVariable("menuId") Long menuId,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

        Long id = menuService.removeMenu(storeId, menuCategoryId, menuId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("메뉴 삭제 성공"));
    }
}
