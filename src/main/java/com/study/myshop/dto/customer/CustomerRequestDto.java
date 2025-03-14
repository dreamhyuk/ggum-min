package com.study.myshop.dto.customer;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRequestDto {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String username;

    private String password;
    private String phoneNumber;

    /* Address */
    private String city;
    private String street;
    private String zipcode;


    /* Dto -> Entity */
    public Member toEntity() {
        return Member.createCustomer(username, password, phoneNumber, new Address(city, street, zipcode));
    }
}
