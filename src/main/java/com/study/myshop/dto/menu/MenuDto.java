package com.study.myshop.dto.menu;

import com.study.myshop.domain.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDto {

    private Long menuId;
    private String name;
    private int price;

    public MenuDto(Menu menu) {
        menuId = menu.getId();
        name = menu.getName();
        price = menu.getPrice();
    }

//    public Menu toEntity() {
//        return new Menu(name, price);
//    }
}
