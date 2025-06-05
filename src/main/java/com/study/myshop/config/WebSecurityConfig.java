package com.study.myshop.config;

import com.study.myshop.authentication.UserDetailsServiceImpl;
import com.study.myshop.security.CustomAuthenticationSuccessHandler;
import com.study.myshop.security.JwtAuthenticationFilter;
import com.study.myshop.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    // 1. API 필터체인 - JWT 기반, Stateless
    @Bean
    @Order(1) // 우선순위 높게 (먼저 적용)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**") // API 요청만 처리
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/new/**", "/favicon.ico").permitAll()
                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/api/owner/**").hasRole("STORE_OWNER")
                        .requestMatchers("/api/rider/**").hasRole("RIDER")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/owner/**").hasRole("STORE_OWNER")
                        .requestMatchers("/rider/**").hasRole("RIDER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 2. 웹 UI 필터체인 - 세션 로그인, CSRF 활성화 가능
    @Bean
    @Order(2) // API 필터체인 다음에 적용
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // 모든 요청 (API 제외한 나머지)
                .csrf(csrf -> csrf.disable()) // 필요하면 enable, 아니면 disable 해도 됨
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/members/login", "/new/**", "/css/**", "/js/**",
                                "/favicon.ico").permitAll()
//                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
//                        .requestMatchers("/owner/**").hasRole("STORE_OWNER")
//                        .requestMatchers("/rider/**").hasRole("RIDER")
                        .anyRequest().authenticated()
                )
//                .formLogin(form -> form
//                        .loginPage("/members/login")
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    //1. Web + session 처리용 필터 체인
/*    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(new AntPathRequestMatcher("/**")) // 웹 UI 경로 분리 (예: /**)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests((requests) -> requests
                        //역할별 접근 제어
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/owner/**").hasRole("STORE_OWNER")
                        .requestMatchers("/rider/**").hasRole("RIDER")

                        //공통 접근 허용 경로
                        .requestMatchers("/", "/new/**", "/members/login", "/css/**", "/js/**").permitAll()
                        //그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
                )
                .securityMatcher("/**") //모든 요청
                .logout(logout -> logout.permitAll())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //2. API용 JWT 처리 필터 체인
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(new AntPathRequestMatcher("/api/**")) // API 경로 분리
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/new/**").permitAll()
//                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
//                        .requestMatchers("/owner/**").hasRole("STORE_OWNER")
//                        .requestMatchers("/rider/**").hasRole("RIDER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

}
