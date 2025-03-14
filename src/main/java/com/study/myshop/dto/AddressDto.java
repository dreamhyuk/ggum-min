package com.study.myshop.dto;

import com.study.myshop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {

    private String city;
    private String street;
    private String zipcode;

    public Address toEntity() {
        return new Address(city, street, zipcode);
    }
}
