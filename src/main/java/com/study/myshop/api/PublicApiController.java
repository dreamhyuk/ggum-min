package com.study.myshop.api;

import com.study.myshop.dto.HomeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@Slf4j
public class PublicApiController {

    @GetMapping("/home")
    public ResponseEntity<HomeDto> home(Model model) {
        HomeDto homeData = new HomeDto("안녕하세요!", "로그인 후 서비스를 이용하세요.");
        return ResponseEntity.ok(homeData);
    }

}
