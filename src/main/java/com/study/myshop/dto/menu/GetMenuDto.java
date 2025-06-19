package com.study.myshop.dto.menu;

import com.study.myshop.domain.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMenuDto {

    private String menuName;
    private int price;

    public GetMenuDto(Menu menu) {
        this.menuName = menu.getName();
        this.price = menu.getPrice();
    }
}
