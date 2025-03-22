package com.study.myshop;

import com.study.myshop.domain.Address;
import com.study.myshop.domain.member.Member;
import com.study.myshop.dto.owner.OwnerRequestDto;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }



    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = Member.createCustomer("userA", "123", "111-111",
                    new Address("city", "street", "zipcode"));
            em.persist(member);
        }

        public void dbInit2() {
            OwnerRequestDto request = new OwnerRequestDto("userB", "password", "123-123", "12345");
            Member member = Member.createOwner2(request);

            em.persist(member);
        }

    }
}
