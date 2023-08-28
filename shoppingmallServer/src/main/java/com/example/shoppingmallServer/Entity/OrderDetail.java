package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue
    private int orderDetailKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_key")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_key")
    private Item item;

    private int orderDetailPrice;

    private int orderDetailCount;
}
