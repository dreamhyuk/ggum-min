package com.study.myshop;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import com.study.myshop.domain.category.MenuCategory;
import com.study.myshop.domain.category.StoreCategory;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.profile.OwnerProfile;
import com.study.myshop.domain.menu.Menu;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//        initService.dbInit4();
//        initService.dbInit5();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            String password1 = passwordEncoder.encode("123");
            Member member1 = Member.createCustomer("customerA", password1, "111-111",
                    new Address("서울", "성내", "55355"));
            em.persist(member1);

            String password2 = passwordEncoder.encode("456");
            Member member2 = Member.createCustomer("customerB", password2, "222-222",
                    new Address("런던", "street", "zipcode"));
            em.persist(member2);

            String password3 = passwordEncoder.encode("789");
            Member member3 = Member.createCustomer("customerC", password3, "333-333",
                    new Address("마드리드", "거리", "바모스"));
            em.persist(member3);

        }

        public void dbInit2() {
            //StoreCategory는 서버에서 데이터가 이미 들어가 있다.(한식, 중식, 패스트푸드.. 등)
            StoreCategory storeCategory1 = StoreCategory.createCategory("한식");
            em.persist(storeCategory1);
            StoreCategory storeCategory2 = StoreCategory.createCategory("중식");
            em.persist(storeCategory2);
            StoreCategory storeCategory3 = StoreCategory.createCategory("치킨");
            em.persist(storeCategory3);
            StoreCategory storeCategory4 = StoreCategory.createCategory("카페");
            em.persist(storeCategory4);
            StoreCategory storeCategory5 = StoreCategory.createCategory("패스트 푸드");
            em.persist(storeCategory5);
            StoreCategory storeCategory6 = StoreCategory.createCategory("분식");
            em.persist(storeCategory6);
            StoreCategory storeCategory7 = StoreCategory.createCategory("디저트");
            em.persist(storeCategory7);

            String password1 = passwordEncoder.encode("1q2w3e4r");
            Member member1 = Member.createOwner("ownerA", password1, "123-123", "1234");
            em.persist(member1);

            String password2 = passwordEncoder.encode("1234");
            Member member2 = Member.createOwner("ownerB", password2, "1234-1234", "5678");
            em.persist(member2);

            //가게 생성
            List<StoreCategory> categories1 = List.of(storeCategory1, storeCategory6);
            Store store1 = Store.create("한정식", new Address("서울", "잠실", "234"),
                    member1.getOwnerProfile(), categories1);
            em.persist(store1);

            List<StoreCategory> categories2 = List.of(storeCategory3, storeCategory5);
            Store store2 = Store.create("교촌", new Address("바르셀로나", "몰라", "바모스!"),
                    member2.getOwnerProfile(), categories2);
            em.persist(store2);

            List<StoreCategory> categories3 = List.of(storeCategory4, storeCategory7);
            Store store3 = Store.create("스타벅스", new Address("런던", "첼시", "9090"),
                    member1.getOwnerProfile(), categories3);
            em.persist(store3);

            List<StoreCategory> categories4 = List.of(storeCategory1, storeCategory3);
            Store store4 = Store.create("한식 치킨", new Address("서울", "강남", "4545"),
                    member2.getOwnerProfile(), categories4);
            em.persist(store4);

        }

        public void dbInit3() {
            Member member = Member.createRider("riderA", "123", "222-222", "car");
            em.persist(member);
        }


/*
        public void dbInit4() {
            String password1 = passwordEncoder.encode("123");
            Member member1 = Member.createOwner("ownerC", password1, "123-123", "1234");
            OwnerProfile ownerProfile1 = member1.getOwnerProfile();
            em.persist(member1);

            String password2 = passwordEncoder.encode("1234");
            Member member2 = Member.createOwner("ownerD", password2, "456-456", "4567");
            OwnerProfile ownerProfile2 = member2.getOwnerProfile();
            em.persist(member2);

            StoreCategory storeCategory1 = StoreCategory.createCategory("한식");
            em.persist(storeCategory1);
            StoreCategory storeCategory2 = StoreCategory.createCategory("중식");
            em.persist(storeCategory2);
            StoreCategory storeCategory3 = StoreCategory.createCategory("치킨");
            em.persist(storeCategory3);
            StoreCategory storeCategory4 = StoreCategory.createCategory("카페");
            em.persist(storeCategory4);

            StoreCategoryMapping mapping1 = StoreCategoryMapping.createMapping(storeCategory1);
            StoreCategoryMapping mapping2 = StoreCategoryMapping.createMapping(storeCategory2);
            StoreCategoryMapping mapping3 = StoreCategoryMapping.createMapping(storeCategory3);
            StoreCategoryMapping mapping4 = StoreCategoryMapping.createMapping(storeCategory4);

            Address address1 = new Address("서울", "명동", "123");
            Address address2 = new Address("바르셀로나", "거리", "456");

            Store store1 = Store.createStore("storeA", address1, ownerProfile1, mapping1, mapping2);
            em.persist(store1);
            Store store2 = Store.createStore("storeB", address2, ownerProfile2, mapping3, mapping4);
            em.persist(store2);
        }

        public void dbInit5() {

            String password = passwordEncoder.encode("5555");
            Member member1 = Member.createOwner("비비큐owner", password, "555-555", "5656");
            em.persist(member1);

            Address address = new Address("서울", "성내", "5555");
            OwnerProfile ownerProfile = member1.getOwnerProfile();

            StoreCategory storeCategory1 = StoreCategory.createCategory("chicken");
            StoreCategory storeCategory2 = StoreCategory.createCategory("pizza");
            StoreCategory storeCategory3 = StoreCategory.createCategory("fast_food");
            em.persist(storeCategory1);
            em.persist(storeCategory2);
            em.persist(storeCategory3);

            StoreCategoryMapping mapping1 = StoreCategoryMapping.createMapping(storeCategory1);
            StoreCategoryMapping mapping2 = StoreCategoryMapping.createMapping(storeCategory2);
            StoreCategoryMapping mapping3 = StoreCategoryMapping.createMapping(storeCategory3);

            List<StoreCategoryMapping> storeCategoryMappings1 = new ArrayList<>();
            storeCategoryMappings1.add(mapping1);
            storeCategoryMappings1.add(mapping3);

            Store store = Store.createStore("비비큐", address, ownerProfile, storeCategoryMappings1);
            em.persist(store);

            MenuCategory menuCategory1 = MenuCategory.createCategory("메인");
            MenuCategory menuCategory2 = MenuCategory.createCategory("사이드");
            MenuCategory menuCategory3 = MenuCategory.createCategory("음료");
            em.persist(menuCategory1);
            em.persist(menuCategory2);
            em.persist(menuCategory3);

            Menu menu1 = Menu.createMenu("황금올리브", 19000, menuCategory1, store);
            em.persist(menu1);
            Menu menu2 = Menu.createMenu("코카콜라", 3000, menuCategory3, store);
            em.persist(menu2);
            Menu menu3 = Menu.createMenu("감자튀김", 6000, menuCategory2, store);
            em.persist(menu3);
        }

 */

    }
}
