package com.study.myshop.dto.menu;

import com.study.myshop.domain.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMenuResponse {

    private Long menuId;
    private String menuName;
    private int price;

    public UpdateMenuResponse(Menu menu) {
        this.menuId = menu.getId();
        this.menuName = menu.getName();
        this.price = menu.getPrice();
    }
}
