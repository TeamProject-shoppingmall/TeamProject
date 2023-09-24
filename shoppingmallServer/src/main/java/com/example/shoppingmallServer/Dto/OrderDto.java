package com.example.shoppingmallServer.Dto;

import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.Order;
import com.example.shoppingmallServer.Enum.OrderStatusEnum;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private int memberKey;
    private String orderReceiver;
    private String orderPhone;
    private String orderZipcode;
    private String orderAddr;
    private String orderAddrDetail;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;
    private int itemKey;
    private int orderCount;
    private int orderPrice;
    private int totalPrice;

    public List<Object> getOrderDetail() {
        List<Object> detail = new ArrayList<>();
        detail.add(this.orderCount);
        detail.add(this.orderPrice);
        detail.add(this.totalPrice);
        detail.add(this.orderDate);
        return detail;
    }
    public List<Object> getOrder() {
        List<Object> order = new ArrayList<>();
        order.add(this.orderReceiver);
        order.add(this.orderPhone);
        order.add(this.orderZipcode);
        order.add(this.orderAddr);
        order.add(this.orderAddrDetail);
        return order;
    }
}

