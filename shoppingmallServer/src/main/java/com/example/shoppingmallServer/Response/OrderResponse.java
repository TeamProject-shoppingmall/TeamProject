package com.example.shoppingmallServer.Response;

import com.example.shoppingmallServer.Entity.OrderDetail;
import com.example.shoppingmallServer.Enum.OrderStatusEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class OrderResponse {
    private int orderKey;
    private int memberKey;
    private String orderAddr;
    private String orderAddrDetail;
    private String orderPhone;
    private String orderReceiver;
    private String orderZipcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate orderDate;
//    private int itemKey;
    private int orderCount;
    private int orderPrice;
    private int totalPrice;
    private OrderStatusEnum orderStatusEnum;

    public OrderResponse(OrderDetail orderDetail) {
        this.orderKey = orderDetail.getOrder().getOrderKey();
        this.memberKey = orderDetail.getOrder().getMember().getMemberKey();
        this.orderAddr = orderDetail.getOrder().getOrderAddr();
        this.orderAddrDetail = orderDetail.getOrder().getOrderAddrDetail();
        this.orderPhone = orderDetail.getOrder().getOrderPhone();
        this.orderReceiver = orderDetail.getOrder().getOrderReceiver();
        this.orderZipcode = orderDetail.getOrder().getOrderZipcode();
        this.orderDate = orderDetail.getOrderDate();
        this.orderCount = orderDetail.getOrderCount();
        this.orderPrice = orderDetail.getOrderPrice();
        this.totalPrice = orderDetail.getTotalPrice();
        this.orderStatusEnum = orderDetail.getOrderStatus();
    }
}
