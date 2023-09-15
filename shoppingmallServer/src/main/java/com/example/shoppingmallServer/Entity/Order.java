package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Enum.OrderStatusEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_key")
    private int orderKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key") // 이 부분을 수정해야 함
    private Member member;

    private int totalPrice;

    private Date orderDate;

    private OrderStatusEnum orderStatus;

    private String orderReceiver;

    private String orderPhone;

    private String orderZipcode;

    private String orderAddr;

    private String orderDetailAddr;

    private String orderRequest;

    private int orderCount;
}
