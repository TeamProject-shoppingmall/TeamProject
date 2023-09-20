package com.example.shoppingmallServer;

import com.example.shoppingmallServer.Dto.OrderDto;
import com.example.shoppingmallServer.Entity.Order;
import com.example.shoppingmallServer.Entity.OrderDetail;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.OrderRepository;
import com.example.shoppingmallServer.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void 주문테스트() {
        OrderDto orderDto = new OrderDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orderDto.setOrderAddr("부천");
        orderDto.setOrderCount(3);
        orderDto.setOrderReceiver("이주훈");
        orderDto.setOrderDate(LocalDate.parse("2023-09-19 18:03:32", formatter));
        orderDto.setOrderPhone("010-1111-1111");
        orderDto.setOrderPrice(15000);
        orderDto.setTotalPrice(orderDto.getOrderPrice() * orderDto.getOrderCount());
        orderDto.setOrderZipcode("11111");
        orderDto.setMemberKey(1);
        orderDto.setItemKey(2);
        orderDto.setOrderAddrDetail("우리집");

        orderService.orderInsert(orderDto);
    }

//    @Test
//    @Transactional
//    public void 주문조회테스트() {
//        List<OrderDetail> allById = orderService.findAllById(1);
//        for (OrderDetail or: allById) {
//            System.out.println(or.getOrder().getOrderPhone());
//            System.out.println(or.getTotalPrice());
//        }
//    }
}