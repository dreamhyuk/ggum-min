package com.study.myshop.dto;

import com.study.myshop.domain.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDto {

    private AddressDto addressDto;
    private DeliveryStatus deliveryStatus;
}
