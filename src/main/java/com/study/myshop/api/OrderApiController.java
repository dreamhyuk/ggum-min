package com.study.myshop.api;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.common.ApiResponse;
import com.study.myshop.domain.Order;
import com.study.myshop.domain.OrderMenu;
import com.study.myshop.dto.order.CreateOrderRequest;
import com.study.myshop.dto.order.CreateOrderResponse;
import com.study.myshop.dto.order.OrderQueryDto;
import com.study.myshop.repository.order.OrderSearch;
import com.study.myshop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    /* 등록 */
    @PostMapping("/stores/{storeId}/orders")
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
            @PathVariable("storeId") Long storeId,
            @RequestBody @Valid CreateOrderRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long orderId = orderService.saveOrder(storeId, request, userDetails);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "주문 생성.", new CreateOrderResponse(orderId)));
    }


    /* 수정은 없고, 주문 취소 후 재주문하는 방식. */


    /* 삭제 */
    @DeleteMapping("/stores/{storeId}/orders/{orderId}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable("storeId") Long storeId,
            @PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();

        orderService.cancelOrder(storeId, orderId, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("주문이 취소되었습니다."));
    }


    /**
     * 조회
     * 주문 조회할 때 조건을 어떻게 받을 것인가?
     * 방법 1. Get + @RequestParam, OrderSearch 객체에 들어가 값들을 쿼리 파라미터로 받는다.
     * 방법 2. Post + @RequestBody (필터가 복잡할 경우), 조건이 많고 복잡해서 body에 JSON형식으로 담고 싶으면 @PostMapping으로.
     */
    @GetMapping("/orders/search")
    public ResponseEntity<ApiResponse<List<OrderQueryDto>>> getOrders(OrderSearch orderSearch) {

        List<Order> orders = orderService.findOrders(orderSearch);

        List<OrderQueryDto> result = orders.stream()
                .map(o -> new OrderQueryDto(o))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("주문 조회", result));
    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderQueryDto>>> getOrderList(OrderSearch orderSearch) {

        List<Order> orders = orderService.findOrders(orderSearch);

        List<OrderQueryDto> result = orders.stream()
                .map(o -> new OrderQueryDto(o))
                .collect(toList());

        return ResponseEntity.ok(ApiResponse.success("POST 주문 조회", result));
    }
}
