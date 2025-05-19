package com.study.myshop.dto;

import com.study.myshop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String city;
    private String street;
    private String zipcode;

    public Address toEntity() {
        return new Address(city, street, zipcode);
    }

    public AddressDto(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.zipcode = address.getZipcode();
    }
}
