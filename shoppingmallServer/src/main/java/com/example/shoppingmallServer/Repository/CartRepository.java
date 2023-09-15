package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Cart;
import com.example.shoppingmallServer.Entity.QItem;
import com.example.shoppingmallServer.Exception.FailedInsertCart;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallServer.Entity.QCart.cart;
import static com.example.shoppingmallServer.Entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final EntityManager em;

    public ResponseEntity<String> cartInsert(Cart cart) {
        try {
            em.persist(cart);
            return new ResponseEntity<>("장바구니에 추가되었습니다",HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedInsertCart("장바구니 추가에 실패했습니다.");
        }
    }

    public List<Tuple> findOneById(int key) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.select( cart.cartKey,
                    cart.cartCount,
                    cart.itemKey.itemName,
                    cart.itemKey.itemPrice,
                    cart.itemKey.itemPath,
                    cart.cartCount.multiply(cart.itemKey.itemPrice)).from(cart).join(cart.itemKey, item).where(cart.memberKey.memberKey.eq(key)).fetch();
    }
}
