//package com.study.myshop.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.study.myshop.authentication.CustomUserDetails;
//import com.study.myshop.domain.member.Member;
//import com.study.myshop.domain.member.profile.OwnerProfile;
//import com.study.myshop.dto.store.CreateStoreRequest;
//import com.study.myshop.service.MemberService;
//import com.study.myshop.service.StoreService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@WebMvcTest(StoreApiController.class)
//@ReplaceWithMock(StoreService.class)
//@ReplaceWithMock(MemberService.class)
//class StoreApiControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private StoreService storeService;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private CustomUserDetails testUser;
//
//    @BeforeEach
//    void setUp() {
//        Member member = new Member("owner1", "encodedPwd");
//        member.setOwnerProfile(new OwnerProfile(1L));
//        testUser = new CustomUserDetails(member);
//    }
//
//    @Test
//    void 매장_생성_성공() throws Exception {
//        // given
//        CreateStoreRequest request = new CreateStoreRequest("내 가게", new Address("서울", "중구", "123"));
//        given(storeService.saveStore(any(), any())).willReturn(1L);
//
//        // when & then
//        mockMvc.perform(post("/api/stores")
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(
//                                new UsernamePasswordAuthenticationToken(testUser, null)))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.storeId").value(1));
//    }
//
//    @Test
//    void 가게_이름_검색_성공() throws Exception {
//        // given
//        List<Store> stores = List.of(new Store("족발집"), new Store("보쌈집"));
//        given(storeService.findByStoreName("집")).willReturn(stores);
//
//        // when & then
//        mockMvc.perform(get("/api/stores/search/name")
//                        .param("storeName", "집"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").isArray());
//    }
//
//    @Test
//    void 내_가게_조회_성공() throws Exception {
//        // given
//        Member member = new Member("owner1", "encodedPwd");
//        member.setOwnerProfile(new OwnerProfile(100L));
//        given(memberService.findByUsername("owner1")).willReturn(member);
//
//        List<Store> stores = List.of(new Store("나의 족발집"));
//        given(storeService.findStoresByOwner(100L)).willReturn(stores);
//
//        // when & then
//        mockMvc.perform(get("/api/stores")
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(
//                                new UsernamePasswordAuthenticationToken(testUser, null))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].storeName").value("나의 족발집"));
//    }
//}