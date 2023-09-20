package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Enum.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_detail")
@Getter
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

    private int orderCount;

    private int orderPrice;

    private int totalPrice;

    private LocalDate orderDate;

    private OrderStatusEnum orderStatus;

    public OrderDetail(List<Object> orderDetail, Order order, Item item) {
        this.item = item;
        this.order = order;
        this.orderDate = (LocalDate)orderDetail.get(3);
        this.orderCount = (int)orderDetail.get(0);
        this.orderPrice = (int)orderDetail.get(1);
        this.totalPrice = (int)orderDetail.get(2);
        this.orderStatus = OrderStatusEnum.valueOf("Y");
    }
    public OrderDetail() {

    }

    public static OrderDetail createOrderDetail(List<Object> orderDetail, Order order, Item item) {
        return new OrderDetail(orderDetail, order, item);
    }
}
