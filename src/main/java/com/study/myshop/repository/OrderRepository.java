package com.study.myshop.repository;

import com.study.myshop.domain.Order;
import com.study.myshop.repository.order.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {


}
