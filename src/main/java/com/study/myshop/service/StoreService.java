package com.study.myshop.service;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.MenuCategoryDto;
import com.study.myshop.dto.StoreCategoryDto;
import com.study.myshop.dto.menu.MenuDto;
import com.study.myshop.dto.owner.OwnerDto;
import com.study.myshop.dto.store.CreateStoreRequest;
import com.study.myshop.dto.store.StoreResponseDto;
import com.study.myshop.dto.store.StoreWithCategoriesDto;
import com.study.myshop.dto.store.UpdateStoreInfoRequest;
import com.study.myshop.exception.MemberNotFoundException;
import com.study.myshop.repository.MemberRepository;
import com.study.myshop.repository.MenuRepository;
import com.study.myshop.repository.StoreCategoryRepository;
import com.study.myshop.repository.StoreRepository;
import com.study.myshop.repository.query.MenuCategoryQueryRepository;
import com.study.myshop.repository.query.MenuQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final MenuCategoryQueryRepository menuCategoryQueryRepository;
    private final MenuQueryRepository menuQueryRepository;


    @Transactional
    public Long saveStore(CreateStoreRequest request, CustomUserDetails userDetails) {
        Member member = memberRepository.findById(userDetails.getId())
                .orElseThrow(() -> new MemberNotFoundException("해당 유저 없음."));

        Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
        List<StoreCategoryMapping> mappings = createMappings(request.getCategoryIds());

        Store store = Store.createStore(request.getStoreName(), address, member.getOwnerProfile(), mappings);

        storeRepository.save(store);

        return store.getId();
    }

    /* 전체 조회 */
    public List<Store> findStores() {
        return storeRepository.findAll();
    }

    /* 가게 이름 조회 */
    public List<Store> findByStoreName(String storeName) {
        return storeRepository.findByStoreName(storeName);
    }

    /* 카테고리 검색 */
    public List<Store> findByCategory(Long categoryId) {
        return storeRepository.findByCategoryId(categoryId);
    }

    public List<Store> findByCategoryName(String categoryName) {
        StoreCategory storeCategory = storeCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 없음: " + categoryName));

        return storeRepository.findByCategoryId(storeCategory.getId());
    }


    //클라이언트에서 categoryId로 받기 때문에, 변환하는 메서드를 만들었다.
    private List<StoreCategoryMapping> createMappings(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> {
                    StoreCategory category = storeCategoryRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("잘못된 식별자: " + id));
                    return StoreCategoryMapping.createMapping(category);
                })
                .collect(toList());
    }

    @Transactional
    public void updateInfo(Long storeId, UpdateStoreInfoRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음."));

        store.updateInfo(request);
    }



    public Store findOne(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음."));
    }

    /**
     * store + storeCategory는 fetch join
     * menuCategory와 menu는 각각 개별 조회
     * 후에 그룹핑
     */
    public StoreResponseDto getStoreDetail(Long storeId) {

        Store storeWithCategories = storeRepository.findStoreWithCategories(storeId);
        List<MenuCategoryDto> menuCategoryDtos = menuCategoryQueryRepository.findMenuCategoryDtosByStoreId(storeId);
        List<MenuDto> menuDtos = menuQueryRepository.findMenuDtosByMenuCategoryIds(storeId);

        //메뉴를 카테고리 id 기준으로 그룹핑
        Map<Long, List<MenuDto>> menusByCategoryId = menuDtos.stream()
                .collect(Collectors.groupingBy(MenuDto::getMenuCategoryId));

        //메뉴 카테고리에 메뉴 할당
        for (MenuCategoryDto menuCategoryDto: menuCategoryDtos) {
            List<MenuDto> categoryMenus = menusByCategoryId.getOrDefault(menuCategoryDto.getMenuCategoryId(), Collections.emptyList());
            menuCategoryDto.setMenus(categoryMenus);
        }

        List<StoreCategoryDto> storeCategoryDtos = storeWithCategories.getStoreCategoryMappings().stream()
                .map(scm -> new StoreCategoryDto(scm.getStoreCategory()))
                .collect(toList());

        return StoreResponseDto.from(storeWithCategories, storeCategoryDtos, menuCategoryDtos);
    }

    private static List<Long> toMenuCategoryIds(List<MenuCategoryDto> result) {
        List<Long> menuCategoryIds = result.stream()
                .map(mc -> mc.getMenuCategoryId())
                .collect(toList());
        return menuCategoryIds;
    }



}
