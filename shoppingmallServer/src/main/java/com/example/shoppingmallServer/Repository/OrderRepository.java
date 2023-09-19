package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Order;
import com.example.shoppingmallServer.Entity.OrderDetail;
import com.example.shoppingmallServer.Exception.FailedInsertException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

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
}
