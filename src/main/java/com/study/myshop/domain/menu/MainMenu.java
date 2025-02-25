package com.study.myshop.domain.menu;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MM")
public class MainMenu extends Menu {
}
