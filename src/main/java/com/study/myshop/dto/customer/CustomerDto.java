package com.study.myshop.dto.customer;

import com.study.myshop.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

    private String name;
    private AddressDto addressDto;
}
