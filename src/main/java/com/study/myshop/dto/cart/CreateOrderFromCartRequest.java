package com.study.myshop.dto.cart;

import com.study.myshop.dto.AddressDto;
import lombok.Data;

@Data
public class CreateOrderFromCartRequest {

    private AddressDto address;
}
