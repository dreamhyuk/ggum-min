package com.study.myshop.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.myshop.domain.menu.Menu;
import com.study.myshop.dto.menu.MenuDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.myshop.domain.menu.QMenu.menu;

@Repository
public class MenuQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public MenuQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Menu> findMenusByMenuCategoryIds(List<Long> menuCategoryIds) {
        return query
                .select(menu)
                .from(menu)
                .where(menu.menuCategory.id.in(menuCategoryIds))
                .fetch();
    }

    //생성자를 통해 바로 Dto클래스로 객체를 반환해준다.
    //storeId로 조회해야 한다. menuCategoryId로 하면 N + 1 문제가 생길 수 있다.
    //나중에 그룹핑을 위해 MenuDto의 필드에 menuCategoryId를 넣어줘야 한다.
    public List<MenuDto> findMenuDtosByMenuCategoryIds(Long storeId) {
        return query
                .select(Projections.constructor(MenuDto.class,
                        menu.id,
                        menu.name,
                        menu.price,
                        menu.menuCategory.id
                ))
                .from(menu)
                .where(menu.menuCategory.store.id.eq(storeId))
                .fetch();
    }
}
