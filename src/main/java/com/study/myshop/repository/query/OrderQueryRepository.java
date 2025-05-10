package com.study.myshop.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.OrderStatus;
import com.study.myshop.repository.order.OrderSearch;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.study.myshop.domain.QOrder.order;
import static com.study.myshop.domain.member.QMember.member;
import static com.study.myshop.domain.member.profile.QCustomerProfile.customerProfile;

@Repository
public class OrderQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return query
                .select(order)
                .from(order)
                .join(order.customerProfile, customerProfile)
                .join(customerProfile.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getCustomerName()))
                .limit(100)
                .fetch();
    }


    private static BooleanExpression nameLike(String customerName) {
        if (!StringUtils.hasText(customerName)) {
            return null;
        }
        return member.username.like(customerName);

    }

    private static BooleanExpression statusEq(OrderStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return order.orderStatus.eq(statusCond);
    }

}
