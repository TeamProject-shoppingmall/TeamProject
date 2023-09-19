package com.example.shoppingmallServer;

import com.example.shoppingmallServer.Dto.OrderDto;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.OrderRepository;
import com.example.shoppingmallServer.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        orderDto.setItemKey(1);
        orderDto.setOrderAddrDetail("우리집");

        orderService.orderInsert(orderDto);
    }
}