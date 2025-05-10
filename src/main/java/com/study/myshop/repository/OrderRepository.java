package com.study.myshop.repository;

import com.study.myshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findByIdAndStoreId(Long orderId, Long storeId);
}
