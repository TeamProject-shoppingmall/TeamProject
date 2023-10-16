package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Enum.CategoryEnum;
import com.example.shoppingmallServer.Exception.FailedRemoveException;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallServer.Entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;
    public boolean uploadItem(Item item) {
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

    public Item findOneByImageName(String imageName) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.selectFrom(item).where(item.itemName.eq(imageName)).fetchOne();
    }

    public ResponseEntity<String> removeItem(Item item) {
        try {
            em.remove(item);
            return new ResponseEntity<>("상품 삭제를 성공했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedRemoveException("상품 삭제에 실패했습니다");
        }
    }

    public ResponseEntity<String> modifyItem (Item item) {
        try {
            em.merge(item);
            return new ResponseEntity<>("상품 수정을 성공했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedRemoveException("상품 수정에 실패했습니다.");
        }
    }
}
