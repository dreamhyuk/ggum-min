package com.study.myshop.domain.menu;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DM")
public class DrinkMenu extends Menu{
}
