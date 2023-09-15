package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Enum.CategoryEnum;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallServer.Entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;
    public boolean uploadImage(Item item) {
        try {
            em.persist(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Item> findAllByCategory(String category) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(item).where(item.categoryType.eq(CategoryEnum.valueOf(category))).fetch();
    }

    public Item findOneById(int itemId) {
        return em.find(Item.class, itemId);
    }

    public Item findByItemName(String name) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(item).where(item.itemName.eq(name)).fetchOne();
    }
}
