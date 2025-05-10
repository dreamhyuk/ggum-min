package com.study.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuRequest {

    private Long menuId;
    private int quantity;
}
