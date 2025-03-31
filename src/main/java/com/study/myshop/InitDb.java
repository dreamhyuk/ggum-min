package com.study.myshop;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.MenuCategoryMapping;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.owner.OwnerRequestDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();
        initService.dbInit5();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.createCustomer("userA", "123", "111-111",
                    new Address("city", "street", "zipcode"));
            em.persist(member);
        }

        public void dbInit2() {
            Member member = Member.createOwner("ownerA", "123", "123-123", "1234");
            em.persist(member);
        }

        public void dbInit3() {
            Member member = Member.createRider("riderA", "123", "222-222", "car");
            em.persist(member);
        }

        public void dbInit4() {
            Member member = Member.createOwner("owner", "123", "123-123", "1234");
            em.persist(member);

            StoreCategory storeCategory1 = StoreCategory.createCategory("한식");
            em.persist(storeCategory1);
            StoreCategory storeCategory2 = StoreCategory.createCategory("중식");
            em.persist(storeCategory2);

            StoreCategoryMapping mapping1 = StoreCategoryMapping.createMapping(storeCategory1);
            StoreCategoryMapping mapping2 = StoreCategoryMapping.createMapping(storeCategory2);

            Address address = new Address("서울", "명동", "123");

            Store store = Store.createStore("storeA", address, member, mapping1, mapping2);
            em.persist(store);
        }

        public void dbInit5() {

            MenuCategory menuCategory1 = MenuCategory.createCategory("메인");
            MenuCategory menuCategory2 = MenuCategory.createCategory("사이드");
            MenuCategory menuCategory3 = MenuCategory.createCategory("음료");
            em.persist(menuCategory1);
            em.persist(menuCategory2);
            em.persist(menuCategory3);

            MenuCategoryMapping mapping1 = MenuCategoryMapping.createMapping(menuCategory1);
            MenuCategoryMapping mapping3 = MenuCategoryMapping.createMapping(menuCategory3);

            Menu menu1 = Menu.addMenu("황금올리브", 19000, mapping1);
            em.persist(menu1);
            Menu menu2 = Menu.addMenu("코카콜라", 3000, mapping3);
            em.persist(menu2);

        }

    }
}
