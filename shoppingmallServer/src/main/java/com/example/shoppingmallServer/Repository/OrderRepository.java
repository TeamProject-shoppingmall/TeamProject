package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.Order;
import com.example.shoppingmallServer.Entity.OrderDetail;
import com.example.shoppingmallServer.Exception.FailedInsertException;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallServer.Entity.QOrder.order;
import static com.example.shoppingmallServer.Entity.QOrderDetail.orderDetail;
import static com.example.shoppingmallServer.Entity.QMember.member;
@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public Order insertOrder(Order order) {
        em.persist(order);
        return order;
    }

    public ResponseEntity<String> insertOrderDetail(OrderDetail orderDetail) {
        try {
            em.persist(orderDetail);
            return new ResponseEntity<>("주문이 성공했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedInsertException("주문이 실패했습니다.");
        }
    }

    public List<OrderDetail> findAllById(int memberKey) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(orderDetail)
                .leftJoin(order).on(order.orderKey.eq(orderDetail.order.orderKey))
                .where(orderDetail.order.member.memberKey.eq(memberKey))
                .fetchJoin()
                .fetch();
    }
}
