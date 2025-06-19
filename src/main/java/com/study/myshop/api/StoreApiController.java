package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.store.*;
import com.study.myshop.service.MemberService;
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
    private final MemberService memberService;

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
     * 가게 이름으로 검색
     */
    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<List<StoreSummaryDto>>> searchStoresByName(@RequestParam("storeName") String storeName) {

        List<Store> stores = storeService.findByStoreName(storeName);
        List<StoreSummaryDto> result = stores.stream()
                .map(s -> new StoreSummaryDto(s))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("가게 이름으로 검색", result));
    }

    /**
     * 카테고리별 가게 검색
     */
    //Path Variable 방식 (간단한 조회에 맞음)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<StoreSummaryDto>>> getStoresByCategory(@PathVariable("categoryId") Long categoryId) {

        List<Store> stores = storeService.findByCategory(categoryId);
        List<StoreSummaryDto> result = stores.stream()
                .map(s -> new StoreSummaryDto(s))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("카테고리로 PathVariable 검색", result));
    }

    //Query String 방식. 추가적인 필터 조건이 붙는다면 이 방법이 유리하다. (평점 높은 순, 주문 많은 순 등등..)
    //쿼리 파라미터로 categoryId보다 categoryName을 사용하는 게 더 직관적이다.
    @GetMapping("/search/category")
    public ResponseEntity<ApiResponse<List<StoreSummaryDto>>> searchStoresByCategory(@RequestParam("category-name") String categoryName) {

        List<Store> stores = storeService.findByCategoryName(categoryName);
        List<StoreSummaryDto> result = stores.stream()
                .map(s -> new StoreSummaryDto(s))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("카테고리 RequestParam 검색", result));
    }


    /**
     * 가게 상세 조회
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreResponseDto>> getStore(@PathVariable("storeId") Long storeId) {

        StoreResponseDto storeDetail = storeService.getStoreDetail(storeId);

        return ResponseEntity.ok(ApiResponse.success("가게 상세 조회", storeDetail));
    }


    //내 가게 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<StoreSummaryDto>>> getMyStores(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member owner = memberService.findByUsername(userDetails.getUsername());
        Long ownerId = owner.getOwnerProfile().getId();

        List<Store> stores = storeService.findStoresByOwner(ownerId);

        List<StoreSummaryDto> result = stores.stream()
                .map(s -> new StoreSummaryDto(s))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("내 가게 조회", result));
    }

}
