package com.study.myshop.service;


import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.profile.CustomerProfile;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.OrderMenuRequest;
import com.study.myshop.dto.order.CreateOrderRequest;
import com.study.myshop.repository.*;
import com.study.myshop.repository.query.OrderQueryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // Mockito 프레임워크를 사용하여 JUnit 5 테스트를 실행
class OrderServiceTest {

    // 테스트 대상 클래스에 Mock 객체들을 주입
    @InjectMocks
    private OrderService orderService;

    @Mock private OrderRepository orderRepository;
    @Mock private OrderQueryRepository orderQueryRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private MenuRepository menuRepository;
    @Mock private StoreRepository storeRepository;
    @Mock private CartRepository cartRepository;

    private Member member;
    private Store store;
    private Menu menu;

/*    @BeforeEach
    void setUp() {
        Address address = new Address("city", "street", "zipcode");
        member = Member.createCustomer("user", "pass", "010-0000-0000", address);
        member.addCustomerProfile(new CustomerProfile(address));

        store = Store.createStore("testStore", null, null); // 필요 최소 필드만
        menu = Menu.createMenu("menu1", 1000, null); // 필요 최소 필드만
    }*/


/*    @Test
    @DisplayName("주문 생성 - 성공")
    public void saveOrder_Success() throws Exception {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(member);
        CreateOrderRequest request = new CreateOrderRequest(
                List.of(new OrderMenuRequest(menu.getId(), 2)),
                new Address("city", "street", "zipcode")
        );

        given(memberRepository.findById(userDetails.getId())).willReturn(Optional.of(member));
        given(storeRepository.findById(1L)).willReturn(Optional.of(store));
        given(menuRepository.findById(any())).willReturn(Optional.of(menu));
        given(orderRepository.save(any(Order.class)))
                .willAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(1L); // 가짜 ID 할당
                    return order;
                });

        // when
        Long orderId = orderService.saveOrder(1L, request, userDetails);

        // then
        assertThat(orderId).isEqualTo(1L);

    }*/


}