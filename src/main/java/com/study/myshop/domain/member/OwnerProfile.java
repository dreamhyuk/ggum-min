package com.study.myshop.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OwnerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_profile_id")
    private Long id;

    @OneToOne(mappedBy = "ownerProfile")
    private Member member;
}
