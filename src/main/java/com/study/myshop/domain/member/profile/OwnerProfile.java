package com.study.myshop.domain.member.profile;

import com.study.myshop.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_profile_id")
    private Long id;

    @OneToOne(mappedBy = "ownerProfile", fetch = LAZY)
    private Member member;

    private String businessNumber;

    public OwnerProfile(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
