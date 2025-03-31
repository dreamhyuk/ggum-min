package com.study.myshop.service;

import com.study.myshop.domain.Store;
import com.study.myshop.repository.StoreRepository;
import com.study.myshop.repository.store.StoreSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Long saveStore(Store store) {
        storeRepository.save(store);
        return store.getId();
    }

    public List<Store> findStore() {
        return storeRepository.findAll();
    }


}
