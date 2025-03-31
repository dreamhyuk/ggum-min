package com.study.myshop.repository;

import com.study.myshop.domain.Store;
import com.study.myshop.repository.store.StoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {


}
