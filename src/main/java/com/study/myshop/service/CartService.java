package com.study.myshop.service;

import com.study.myshop.domain.Cart;
import com.study.myshop.domain.CartItem;
import com.study.myshop.domain.member.profile.CustomerProfile;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.cart.CartRequest;
import com.study.myshop.repository.CartRepository;
import com.study.myshop.repository.MemberRepository;
import com.study.myshop.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Long addMenuToCart(Long memberId, CartRequest request) {
        CustomerProfile customer = findCustomerProfile(memberId);

//        CartItem cartItem = CartItem.createCartItem(menu, menu.getPrice(), request.getCount());

        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.createCart();
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));

        cart.addMenu(menu, request.getCount());

        return cart.getId();
    }


    private CustomerProfile findCustomerProfile(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"))
                .getCustomerProfile();
    }
}
