package com.study.myshop.service;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.*;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.cart.CreateOrderFromCartRequest;
import com.study.myshop.dto.order.CreateOrderRequest;
import com.study.myshop.exception.UnauthorizedAccessException;
import com.study.myshop.repository.*;
import com.study.myshop.repository.order.OrderSearch;
import com.study.myshop.repository.query.OrderQueryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final CartRepository cartRepository;

    private final EntityManager em;

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
                    return OrderMenu.createOrderMenu(menu, menu.getPrice(), orderMenuRequest.getCount());
                })
                .collect(toList());

        //배송정보 생성
        Delivery delivery = Delivery.createDelivery(request.getAddress().toEntity());

        //주문 생성
        Order order = Order.createOrder(member.getCustomerProfile(), store, delivery, orderMenus);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 장바구니에 담긴 메뉴를 주문으로 전환한다.
     * 주소를 그냥 현재 유저의 주소로 고정
     * 주문 ID
     */
    @Transactional
    public Long orderFromCart(Long storeId, CustomUserDetails userDetails) {
        Member member = memberRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음."));

        Cart cart = cartRepository.findByCustomerId(member.getCustomerProfile().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니를 찾을 수 없음."));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("장바구니에 담긴 메뉴가 없습니다.");
        }

        // 장바구니의 모든 메뉴가 같은 가게의 것인지 확인
        Store store = cart.getCartItems().get(0).getMenu().getMenuCategory().getStore();
        boolean sameStore = cart.getCartItems().stream()
                .allMatch(ci -> ci.getMenu().getMenuCategory().getStore().equals(store));
        if (!sameStore) {
            throw new IllegalStateException("장바구니에 서로 다른 가게의 메뉴가 포함되어 있습니다.");
        }

        // OrderMenu 생성
        List<OrderMenu> orderMenus = cart.getCartItems().stream()
                .map(cartItem -> OrderMenu.createOrderMenu(
                        cartItem.getMenu(), cartItem.getMenu().getPrice(), cartItem.getCount()))
                .collect(toList());

        // 로그인한 사용자의 주소 사용
        Address address = member.getCustomerProfile().getAddress();
        Delivery delivery = Delivery.createDelivery(address);

        // 주문 생성
        Order order = Order.createOrder(member.getCustomerProfile(), store, delivery, orderMenus);

        orderRepository.save(order);

        cart.getCartItems().clear(); // orphanRemoval = true가 작동하도록
        cartRepository.delete(cart); // 주문 후 장바구니 비우기
        System.out.println("Cart deleted: " + cart.getId());

        return order.getId();
    }
    /**
     * 장바구니에 담긴 메뉴를 주문으로 전환한다.
     * request      배송지(주소) 등 부가 정보만 담긴 DTO
     * customerId  로그인 고객 id
     * 주문 ID
     */
/*    @Transactional
    public Long orderFromCart(CreateOrderFromCartRequest request, CustomUserDetails userDetails) {
        Member member = memberRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음."));

        Cart cart = cartRepository.findByCustomerId(member.getCustomerProfile().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니를 찾을 수 없음."));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("장바구니에 담긴 메뉴가 없습니다.");
        }

        //장바구니에 담긴 메뉴가 모두 같은 가게인지 검증
        Store store = cart.getCartItems().get(0).getMenu().getMenuCategory().getStore(); // 기준 가게
        boolean sameStore = cart.getCartItems().stream()
                .allMatch(ci -> ci.getMenu().getMenuCategory().getStore().equals(store));
        if (!sameStore) {
            throw new IllegalStateException("장바구니에 서로 다른 가게의 메뉴가 포함되어 있습니다.");
        }

        //CartItem -> OrderMenu 로 변환
        List<OrderMenu> orderMenus = cart.getCartItems().stream()
                .map(cartItem -> OrderMenu.createOrderMenu(
                        cartItem.getMenu(), cartItem.getMenu().getPrice(), cartItem.getCount())
                )
                .collect(toList());

        //배송정보 생성
        Delivery delivery = Delivery.createDelivery(request.getAddress().toEntity());

        //주문 생성
        Order order = Order.createOrder(member.getCustomerProfile(), store, delivery, orderMenus);

        //주문 저장
        orderRepository.save(order);

        //장바구니 비우기
        cartRepository.delete(cart);

        return order.getId();
    }*/


    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long storeId, Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndStoreId(orderId, storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없음."));

        if (!order.getCustomerProfile().getMember().getId().equals(userId)) {
            throw new UnauthorizedAccessException("본인의 주문만 취소할 수 있음.");
        }

        order.cancel();
    }


    /**
     * 검색
     */
    //Customer가 자신의 주문 조회
    public List<Order> findOrdersByCustomer(Long userId, OrderSearch orderSearch) {
        return orderQueryRepository.findOrdersByCustomer(userId, orderSearch);
    }

    //Owner가 본인 가게의 주문 조회
    public List<Order> findOrdersByStore(Long userId, Long storeId, OrderSearch orderSearch) {
        Store store = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게 찾을 수 없음."));

        if (!store.getOwnerProfile().getMember().getId().equals(userId)) {
            throw new UnauthorizedAccessException("본인 가게의 주문만 조회할 수 있음.");
        }

        return orderQueryRepository.findOrdersByStore(storeId, orderSearch);
    }


    public Order findOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 없음."));
    }
}
