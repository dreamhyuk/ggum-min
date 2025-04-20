package com.study.myshop.dto.owner;

import com.study.myshop.domain.member.profile.OwnerProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerDto {

    private String name;
    private String businessNumber;

    public OwnerDto(OwnerProfile ownerProfile) {
        name = ownerProfile.getMember().getUsername();
        businessNumber = ownerProfile.getBusinessNumber();
    }

}
