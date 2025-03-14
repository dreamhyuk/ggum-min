package com.study.myshop.domain.member.profile;

import com.study.myshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiderProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_profile_id")
    private Long id;

    @OneToOne(mappedBy = "riderProfile")
    private Member member;

    private String vehicleType;
//    private boolean isAvailable;

    public RiderProfile(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
