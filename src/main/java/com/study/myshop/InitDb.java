//package com.study.myshop;
//
//import com.study.myshop.domain.*;
//import com.study.myshop.domain.category.MenuCategory;
//import com.study.myshop.domain.category.StoreCategory;
//import com.study.myshop.domain.member.Member;
//import com.study.myshop.domain.member.profile.OwnerProfile;
//import com.study.myshop.domain.menu.Menu;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//        initService.dbInit2();
//        initService.dbInit3();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//        private final PasswordEncoder passwordEncoder;
//
//        public void dbInit1() {
//            String password1 = passwordEncoder.encode("123");
//            Member member1 = Member.createCustomer("customerA", password1, "111-111",
//                    new Address("서울", "성내", "55355"));
//            em.persist(member1);
//
//            String password2 = passwordEncoder.encode("456");
//            Member member2 = Member.createCustomer("customerB", password2, "222-222",
//                    new Address("런던", "street", "zipcode"));
//            em.persist(member2);
//
//            String password3 = passwordEncoder.encode("789");
//            Member member3 = Member.createCustomer("customerC", password3, "333-333",
//                    new Address("마드리드", "거리", "바모스"));
//            em.persist(member3);
//
//        }
//
//        public void dbInit2() {
//            //StoreCategory는 서버에서 데이터가 이미 들어가 있다.(한식, 중식, 패스트푸드.. 등)
//            StoreCategory storeCategory1 = StoreCategory.createCategory("한식");
//            em.persist(storeCategory1);
//            StoreCategory storeCategory2 = StoreCategory.createCategory("중식");
//            em.persist(storeCategory2);
//            StoreCategory storeCategory3 = StoreCategory.createCategory("일식");
//            em.persist(storeCategory3);
//            StoreCategory storeCategory4 = StoreCategory.createCategory("치킨");
//            em.persist(storeCategory4);
//            StoreCategory storeCategory5 = StoreCategory.createCategory("피자");
//            em.persist(storeCategory5);
//            StoreCategory storeCategory6 = StoreCategory.createCategory("패스트푸드");
//            em.persist(storeCategory6);
//            StoreCategory storeCategory7 = StoreCategory.createCategory("분식");
//            em.persist(storeCategory7);
//            StoreCategory storeCategory8 = StoreCategory.createCategory("카페");
//            em.persist(storeCategory8);
//
//            String password1 = passwordEncoder.encode("1q2w3e4r");
//            Member member1 = Member.createOwner("ownerA", password1, "123-123", "1234");
//            em.persist(member1);
//
//            String password2 = passwordEncoder.encode("1234");
//            Member member2 = Member.createOwner("ownerB", password2, "1234-1234", "5678");
//            em.persist(member2);
//
//            String password3 = passwordEncoder.encode("1111");
//            Member member3 = Member.createOwner("myOwner", password3, "0000-0000", "909090");
//            em.persist(member3);
//
//            //가게 생성
//            List<StoreCategory> categories1 = List.of(storeCategory1, storeCategory6);
//            Store store1 = Store.create("한정식집", new Address("서울", "잠실", "234"),
//                    member1.getOwnerProfile(), categories1);
//            em.persist(store1);
//
//            List<StoreCategory> categories2 = List.of(storeCategory4, storeCategory6);
//            Store store2 = Store.create("교촌치킨", new Address("바르셀로나", "거리", "바모스!"),
//                    member2.getOwnerProfile(), categories2);
//            em.persist(store2);
//
//            List<StoreCategory> categories3 = List.of(storeCategory8, storeCategory6);
//            Store store3 = Store.create("스타벅스", new Address("런던", "첼시", "9090"),
//                    member1.getOwnerProfile(), categories3);
//            em.persist(store3);
//
//            List<StoreCategory> categories4 = List.of(storeCategory1, storeCategory4);
//            Store store4 = Store.create("한식 치킨", new Address("서울", "강남", "4545"),
//                    member2.getOwnerProfile(), categories4);
//            em.persist(store4);
//
//            List<StoreCategory> categories5 = List.of(storeCategory5, storeCategory6);
//            Store store5 = Store.create("피자헛", new Address("뮌헨", "거리", "123132"),
//                    member3.getOwnerProfile(), categories5);
//            em.persist(store5);
//
//            List<StoreCategory> categories6 = List.of(storeCategory6, storeCategory7);
//            Store store6 = Store.create("엽떡", new Address("서울", "성내", "8887"),
//                    member3.getOwnerProfile(), categories6);
//            em.persist(store6);
//
//            List<StoreCategory> categories7 = List.of(storeCategory2, storeCategory7);
//            Store store7 = Store.create("사천짜장", new Address("부산", "사거리", "545"),
//                    member3.getOwnerProfile(), categories7);
//            em.persist(store7);
//
//            List<StoreCategory> categories8 = List.of(storeCategory3, storeCategory8);
//            Store store8 = Store.create("초밥집", new Address("도쿄", "거리", "0909"),
//                    member3.getOwnerProfile(), categories8);
//            em.persist(store8);
//
//            /**
//             * 교촌
//             */
//            //메뉴 카테고리
//            MenuCategory menuCategory1 = store2.addMenuCategory("오리지널");
//            MenuCategory menuCategory2 = store2.addMenuCategory("콤보");
//            MenuCategory menuCategory3 = store2.addMenuCategory("사이드");
//            //메뉴
//            Menu menu1 = Menu.createMenu("오리지널 반반", 19000, menuCategory1);
//            em.persist(menu1);
//            Menu menu2 = Menu.createMenu("교촌 오리지널", 18000, menuCategory1);
//            em.persist(menu2);
//            Menu menu3 = Menu.createMenu("허니 콤보", 21000, menuCategory2);
//            em.persist(menu3);
//            Menu menu4 = Menu.createMenu("코카콜라", 3000, menuCategory3);
//            em.persist(menu4);
//
//            /**
//             * 한정식
//             */
//            //메뉴 카테고리
//            MenuCategory menuCategory4 = store1.addMenuCategory("한상차림");
//            MenuCategory menuCategory5 = store1.addMenuCategory("찌개류");
//            MenuCategory menuCategory6 = store1.addMenuCategory("백반");
//            //메뉴
//            Menu menu5 = Menu.createMenu("한상차림", 19000, menuCategory4);
//            em.persist(menu5);
//            Menu menu6 = Menu.createMenu("김치찌개", 9000, menuCategory5);
//            em.persist(menu6);
//            Menu menu7 = Menu.createMenu("된장찌개", 9000, menuCategory5);
//            em.persist(menu7);
//            Menu menu8 = Menu.createMenu("불고기 백반", 12000, menuCategory6);
//            em.persist(menu8);
//
//            /**
//             * 스타벅스
//             */
//            //메뉴 카테고리
//            MenuCategory menuCategory7 = store3.addMenuCategory("커피/음료");
//            MenuCategory menuCategory8 = store3.addMenuCategory("디저트");
//            MenuCategory menuCategory9 = store3.addMenuCategory("제품");
//            //메뉴
//            Menu menu9 = Menu.createMenu("아메리카노", 4000, menuCategory7);
//            em.persist(menu9);
//            Menu menu10 = Menu.createMenu("캬라멜마끼야또", 6000, menuCategory7);
//            em.persist(menu10);
//            Menu menu11 = Menu.createMenu("초코빵", 7000, menuCategory8);
//            em.persist(menu11);
//            Menu menu12 = Menu.createMenu("스벅 보틀", 16000, menuCategory9);
//            em.persist(menu12);
//
//            //주문
//            String password = passwordEncoder.encode("123");
//            Member member = Member.createCustomer("myCustomer", password, "000-000",
//                    new Address("서울", "풍납", "00000"));
//            em.persist(member);
//
//            Delivery delivery = Delivery.createDelivery(member.getCustomerProfile().getAddress());
//            em.persist(delivery);
//
//            OrderMenu orderMenu1 = OrderMenu.createOrderMenu(menu1, 19000, 1);
//            OrderMenu orderMenu2 = OrderMenu.createOrderMenu(menu3, 21000, 2);
//            em.persist(orderMenu1);
//            em.persist(orderMenu2);
//
//            List<OrderMenu> orderMenus = new ArrayList<>();
//            orderMenus.add(orderMenu1);
//            orderMenus.add(orderMenu2);
//
//            Order order = Order.createOrder(member.getCustomerProfile(), store2, delivery, orderMenus);
//            em.persist(order);
//
//        }
//
//        public void dbInit3() {
//            Member member = Member.createRider("riderA", "123", "222-222", "car");
//            em.persist(member);
//        }
//
//
//
//    }
//}
