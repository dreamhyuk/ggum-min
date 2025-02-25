package com.study.myshop.domain.menu;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SM")
public class SideMenu extends Menu {
}
