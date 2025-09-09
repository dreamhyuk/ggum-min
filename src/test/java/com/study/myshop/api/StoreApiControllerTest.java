package com.study.myshop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.Address;
import com.study.myshop.domain.Store;
import com.study.myshop.domain.member.profile.OwnerProfile;
import com.study.myshop.dto.store.CreateStoreRequest;
import com.study.myshop.service.MemberService;
import com.study.myshop.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

}