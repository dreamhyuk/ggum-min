package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.Store;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.store.*;
import com.study.myshop.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreApiController {

    private final StoreService storeService;


    /**
     * 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CreateStoreResponse>> saveStore(@RequestBody @Valid CreateStoreRequest request,
                                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = storeService.saveStore(request, userDetails);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "매장 생성!", new CreateStoreResponse(id)));
    }

    /**
     * 수정
     */
    /* 기본 정보 수정 */
    @PutMapping("/{storeId}")
    public ResponseEntity<ApiResponse<UpdateStoreInfoResponse>> updateStoreInfo(
            @PathVariable("storeId") Long storeId,
            @RequestBody @Valid UpdateStoreInfoRequest request) {

        storeService.updateInfo(storeId, request);

        Store store = storeService.findOne(storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(200, "수정 완료",
                        new UpdateStoreInfoResponse(storeId, store.getStoreName(), new AddressDto(store.getAddress()))));
    }


    /**
     * 검색
     */
    //가게 이름으로 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StoreResponseDto>>> searchStoresByName(@RequestParam("storeName") String storeName) {

        List<Store> stores = storeService.findByStoreName(storeName);
        List<StoreResponseDto> result = stores.stream()
                .map(s -> new StoreResponseDto(s))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("가게 이름으로 검색", result));
    }

    //카테고리별 가게 검색
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<StoreResponseDto>>> getStoresByCategory(@PathVariable("categoryId") Long categoryId) {

        List<Store> stores = storeService.findByCategory(categoryId);
        List<StoreResponseDto> result = stores.stream()
                .map(s -> new StoreResponseDto(s))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("카테고리로 검색", result));
    }

}
