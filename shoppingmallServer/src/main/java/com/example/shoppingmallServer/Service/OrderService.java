package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.CartDto;
import com.example.shoppingmallServer.Dto.OrderDto;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.Order;
import com.example.shoppingmallServer.Entity.OrderDetail;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.OrderRepository;
import com.example.shoppingmallServer.Response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public ResponseEntity<String> orderInsert(OrderDto orderDto) {
        Member findMember = memberRepository.findOneById(orderDto.getMemberKey());
        if (findMember == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }

        Order order = Order.createOrder(orderDto.getOrder(), findMember);

        Item findItem = itemRepository.findOneById(orderDto.getItemKey());
        if (findItem == null) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        Order findOrder = orderRepository.insertOrder(order);
        if (findOrder == null) {
            throw new NotFoundException("주문 정보가 없습니다..");
        }

        OrderDetail orderDetail = OrderDetail.createOrderDetail(orderDto.getOrderDetail(), findOrder, findItem);

        return orderRepository.insertOrderDetail(orderDetail);
    }

    public ResponseEntity<List<OrderResponse>> findAllById(int memberKey) {
        Member findMember = memberRepository.findOneById(memberKey);
        if (findMember == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }
        List<OrderDetail> allById = orderRepository.findAllById(memberKey);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderDetail detail : allById) {
            orderResponses.add(new OrderResponse(detail));
        }
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }
}
