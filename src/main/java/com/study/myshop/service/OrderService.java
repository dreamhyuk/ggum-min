package com.study.myshop.service;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Delivery;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.OrderMenu;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.OrderMenuRequest;
import com.study.myshop.dto.order.CreateOrderRequest;
import com.study.myshop.exception.UnauthorizedAccessException;
import com.study.myshop.repository.MemberRepository;
import com.study.myshop.repository.MenuRepository;
import com.study.myshop.repository.OrderRepository;
import com.study.myshop.repository.StoreRepository;
import com.study.myshop.repository.order.OrderSearch;
import com.study.myshop.repository.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    /**
     * 주문
     */
    @Transactional
    public Long saveOrder(Long storeId, CreateOrderRequest request, CustomUserDetails userDetails) {
        //엔티티 조회
        Member member = memberRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게 없음."));

        List<OrderMenu> orderMenus = request.getOrderMenus().stream()
                .map(orderMenuRequest -> {
                    Menu menu = menuRepository.findById(orderMenuRequest.getMenuId())
                            .orElseThrow(() -> new IllegalArgumentException("메뉴 없음."));
                    return new OrderMenu(menu, menu.getPrice(), orderMenuRequest.getQuantity());
                })
                .collect(toList());

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(request.getAddressDto().toEntity());

        //주문 생성
        Order order = Order.createOrder(member.getCustomerProfile(), delivery, orderMenus);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long storeId, Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndStoreId(orderId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음."));

        if (!order.getCustomerProfile().getId().equals(userId)) {
            throw new UnauthorizedAccessException("본인의 주문만 취소할 수 있음.");
        }

        order.cancel();
    }


    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderQueryRepository.findAll(orderSearch);
    }


    public Order findOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 없음."));
    }
}
