package com.study.myshop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.myshop.annotation.WithCustomMockUser;
import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.authentication.UserDetailsServiceImpl;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.Role;
import com.study.myshop.domain.member.profile.OwnerProfile;
import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.store.CreateStoreRequest;
import com.study.myshop.dto.store.StoreResponseDto;
import com.study.myshop.dto.store.UpdateStoreInfoRequest;
import com.study.myshop.service.MemberService;
import com.study.myshop.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreApiController.class)
class StoreApiControllerTest {

    // HTTP 요청을 시뮬레이션하는 MockMvc 객체
    @Autowired
    private MockMvc mockMvc;

    // JSON 객체로 변환하기 위한 ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreService storeService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @Test
    @DisplayName("매장 등록 요청 성공")
    @WithMockUser(username = "ownerUser", roles = {"OWNER"})
    void saveStore_success() throws Exception {
        // given
        Long savedId = 1L;

        // CreateStoreRequest를 id 없이 직접 세팅
        CreateStoreRequest request = new CreateStoreRequest();
        request.setStoreName("testStore");
        request.setCity("city");
        request.setStreet("street");
        request.setZipcode("zipcode");
        request.setCategoryIds(new ArrayList<>());

        // storeService.saveStore() 호출 시 가짜 ID를 반환하도록 설정
        when(storeService.saveStore(any(), any())).thenReturn(savedId);

        // 테스트 시작 시 Mock이 제대로 적용되었는지 로그 확인
        System.out.println("====================================================");
        System.out.println(storeService);
        System.out.println(Mockito.mockingDetails(storeService).isMock());
        System.out.println("====================================================");

        // when
        mockMvc.perform(post("/api/stores")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.data.id").value(savedId));
    }

    @Test
    @DisplayName("매장 정보 수정 성공")
    @WithMockUser(username = "ownerUser", roles = {"OWNER"})
    void updateStoreInfo_success() throws Exception {
        // given
        Long storeId = 1L;
        UpdateStoreInfoRequest request = new UpdateStoreInfoRequest();
        request.setStoreName("Updated Store Name");
        request.setAddressDto(new AddressDto("newCity", "newStreet", "newZipcode"));

        Store updatedStore = Store.createStore("Updated Store Name",
                new Address("newCity", "newStreet", "newZipcode"),
                (OwnerProfile) null, Collections.emptyList());
        ReflectionTestUtils.setField(updatedStore, "id", storeId);

        // storeService.findOne() 호출 시 업데이트된 가짜 Store 객체 반환하도록 설정
        when(storeService.findOne(anyLong())).thenReturn(updatedStore);

        // when
        mockMvc.perform(put("/api/stores/{storeId}", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("수정 완료"))
                .andExpect(jsonPath("$.data.storeName").value("Updated Store Name"));
    }


    @Test
    @DisplayName("가게 이름으로 검색")
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void searchStoresByName_success() throws Exception {
        // given
        String storeName = "Test Store";
        // Mock 객체가 반환할 가짜 데이터를 준비합니다.
        Store mockStore = Store.createStore("testStore", new Address("city", "street", "zipcode"),
                new OwnerProfile("1234"),
                new ArrayList<>());
        List<Store> mockStores = singletonList(mockStore);

        // storeService.findByStoreName()이 호출되면 가짜 데이터를 반환하도록 설정합니다.
        when(storeService.findByStoreName(storeName)).thenReturn(mockStores);

        // when
        // MockMvc를 사용해 GET 요청을 보냅니다.
        mockMvc.perform(get("/api/stores/search/name")
                        .param("storeName", storeName) // 쿼리 파라미터 설정
                        .contentType(MediaType.APPLICATION_JSON))

                // then
                // HTTP 상태 코드가 200 OK인지 확인합니다.
                .andExpect(status().isOk())
                // 응답 JSON의 특정 필드 값이 예상과 일치하는지 확인합니다.
                .andExpect(jsonPath("$.message").value("가게 이름으로 검색"))
                .andExpect(jsonPath("$.data[0].storeName").value(mockStore.getStoreName()));
    }

    @Test
    @DisplayName("Path Variable 방식으로 카테고리 검색")
    @WithMockUser(username = "testCustomer", roles = "USER")
    public void getStoresByCategoryTest() throws Exception {
        //given
        Long categoryId = 1L;

        Store store1 = Store.createStore("testStore1", null, (OwnerProfile) null, Collections.emptyList());
        Store store2 = Store.createStore("testStore2", null, (OwnerProfile) null, Collections.emptyList());

        List<Store> stores = Arrays.asList(store1, store2);

        // storeService.findByCategory(1L) 호출 시 mockStores 반환하도록 설정
        given(storeService.findByCategory(categoryId)).willReturn(stores);

        //when
        mockMvc.perform(get("/api/stores/category/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(2))
                .andExpect(jsonPath("$.data[0].storeName").value("testStore1"))
                .andExpect(jsonPath("$.data[1].storeName").value("testStore2"));
    }

    @Test
    @DisplayName("Query String으로 카테고리 검색")
    @WithMockUser(username = "testCustomer", roles = "USER")
    public void searchStoresByCategoryTest() throws Exception {
        //given
        String categoryName = "한식";
        Store store1 = Store.createStore("store1", null, (OwnerProfile) null, Collections.emptyList());
        Store store2 = Store.createStore("store2", null, (OwnerProfile) null, Collections.emptyList());
        Store store3 = Store.createStore("가게3", null, (OwnerProfile) null, Collections.emptyList());

        List<Store> stores = Arrays.asList(store1, store2, store3);

        given(storeService.findByCategoryName("한식")).willReturn(stores);

        //when & then
        mockMvc.perform(get("/api/stores/search/category")
                .param("category-name", categoryName)) //RequestParam 추가
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(3))
                .andExpect(jsonPath("$.data[0].storeName").value("store1"))
                .andExpect(jsonPath("$.data[1].storeName").value("store2"))
                .andExpect(jsonPath("$.data[2].storeName").value("가게3"));

    }
    
    @Test
    @DisplayName("가게 상세 조회 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    public void getStoreTest() throws Exception {
        //given
        Long storeId = 1L;

        StoreResponseDto response = new StoreResponseDto(1L, "testStore", null,
                null, Collections.emptyList(), Collections.emptyList());

        given(storeService.getStoreDetail(storeId)).willReturn(response);

        //when & then
        mockMvc.perform(get("/api/stores/{storeId}", storeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("가게 상세 조회"))
                .andExpect(jsonPath("$.data.storeId").value(1L))
                .andExpect(jsonPath("$.data.name").value("testStore"));
    }

    @Test
    @DisplayName("내 가게 조회 성공")
    @WithCustomMockUser(username = "testOwner", role = Role.ROLE_STORE_OWNER)
    public void getMyStoresTest() throws Exception {
        Long ownerProfileId = 1L;

        // Mock 객체
        Member mockMember = Mockito.mock(Member.class);
        OwnerProfile mockOwnerProfile = Mockito.mock(OwnerProfile.class);

        given(mockMember.getOwnerProfile()).willReturn(mockOwnerProfile);
        given(mockOwnerProfile.getId()).willReturn(ownerProfileId);

        given(memberService.findByUsername("testOwner")).willReturn(mockMember);
        given(storeService.findStoresByOwner(ownerProfileId)).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/stores").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(0));
    }

/*    @Test
    @DisplayName("내 가게 조회 성공")
    @WithCustomMockUser(username = "testOwner", role = "OWNER")
    public void getMyStoresTest() throws Exception {
        // given
        String username = "testOwner";
        Long ownerProfileId = 1L;

        // 1. Member와 OwnerProfile Mock 객체 생성
        Member mockMember = Mockito.mock(Member.class);
        OwnerProfile mockOwnerProfile = Mockito.mock(OwnerProfile.class);

        // 2. Mock 객체의 동작 설정
        given(mockMember.getOwnerProfile()).willReturn(mockOwnerProfile);
        given(mockOwnerProfile.getId()).willReturn(ownerProfileId);

        // 3. Service 메서드 Mocking 설정
        //    findByUsername()이 @WithMockUser의 username을 받아 MockMember 객체를 반환하도록 설정
        given(memberService.findByUsername(username)).willReturn(mockMember);
        //    findStoresByOwner()가 MockOwnerProfile의 id를 받아 가게 목록을 반환하도록 설정
        given(storeService.findStoresByOwner(ownerProfileId)).willReturn(Collections.emptyList());

        // when & then
        mockMvc.perform(get("/api/stores")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(0));
    }*/


}