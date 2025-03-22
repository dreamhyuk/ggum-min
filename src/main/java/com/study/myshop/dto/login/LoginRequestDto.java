package com.study.myshop.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
