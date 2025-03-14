package com.study.myshop.dto.owner;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerRequestDto {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String username;

    private String password;
    private String phoneNumber;

    private String businessNumber;
}
