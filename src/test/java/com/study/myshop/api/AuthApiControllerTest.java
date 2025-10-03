package com.study.myshop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.myshop.dto.customer.CustomerRequestDto;
import com.study.myshop.security.JwtTokenProvider;
import com.study.myshop.service.AuthService;
import com.study.myshop.service.RefreshTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // get, post 등
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;   // status, jsonPath 등
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;  // print()

@WebMvcTest(AuthApiController.class)
class AuthApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private RefreshTokenService refreshTokenService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @WithMockUser
    @DisplayName("고객 회원가입 성공")
    void saveCustomer_success() throws Exception {
        CustomerRequestDto requestDto = new CustomerRequestDto("testCustomer", "testPass",
                "010-1010-1010", "city", "street", "zipcode");

        given(authService.joinCustomer(any(CustomerRequestDto.class))).willReturn(1L);

        mockMvc.perform(post("/api/auth/new/customer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

}