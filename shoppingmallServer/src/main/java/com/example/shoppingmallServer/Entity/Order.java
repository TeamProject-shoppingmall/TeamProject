package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_key")
    private int orderKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key") // 이 부분을 수정해야 함
    private Member member;

    private String orderReceiver;

    private String orderPhone;

    private String orderZipcode;

    private String orderAddr;

    private String orderAddrDetail;

    public Order(List<Object> order, Member member) {
        this.member = member;
        this.orderReceiver = (String) order.get(0);
        this.orderPhone = (String) order.get(1);
        this.orderZipcode = (String) order.get(2);
        this.orderAddr = (String) order.get(3);
        this.orderAddrDetail = (String) order.get(4);
    }
    public Order() {

    }

    public static Order createOrder(List<Object> order, Member member) {
       return new Order(order, member);
   }
}
