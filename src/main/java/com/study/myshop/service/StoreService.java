package com.study.myshop.service;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.store.CreateStoreRequest;
import com.study.myshop.dto.store.UpdateStoreInfoRequest;
import com.study.myshop.exception.MemberNotFoundException;
import com.study.myshop.repository.MemberRepository;
import com.study.myshop.repository.MenuRepository;
import com.study.myshop.repository.StoreCategoryRepository;
import com.study.myshop.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final MenuRepository menuRepository;


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

//    @Transactional
//    public void addMenus(Long storeId, UpdateStoreMenuRequest request) {
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음!"));
//
//        List<Menu> findMenus = menuRepository.findAllById(request.getMenuIds());
//
//        store.addMenus(findMenus);
//    }
//
//    @Transactional
//    public void removeMenus(Long storeId, UpdateStoreMenuRequest request) {
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음."));
//
//        List<Menu> findMenus = menuRepository.findAllById(request.getMenuIds());
//
//        store.removeMenus(findMenus);
//    }


    public Store findOne(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장을 찾을 수 없음."));
    }


}
