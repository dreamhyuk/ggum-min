package com.study.myshop.dto.order;

import com.study.myshop.dto.AddressDto;
import com.study.myshop.dto.OrderMenuRequest;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CreateOrderRequest {

    private AddressDto address;

    private List<OrderMenuRequest> orderMenus;

}
