package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.CartDto;
import com.example.shoppingmallServer.Dto.OrderDto;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.Order;
import com.example.shoppingmallServer.Entity.OrderDetail;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public ResponseEntity<String> orderInsert(OrderDto orderDto) {
        Member findMember = memberRepository.findOneById(orderDto.getMemberKey());
        Order order = Order.createOrder(orderDto.getOrder(), findMember);

        Order findOrder = orderRepository.insertOrder(order);
        Item findItem = itemRepository.findOneById(orderDto.getItemKey());

        OrderDetail orderDetail = OrderDetail.createOrderDetail(orderDto.getOrderDetail(), findOrder, findItem);

        return orderRepository.insertOrderDetail(orderDetail);
    }
}
