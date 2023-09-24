package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.OrderDto;
import com.example.shoppingmallServer.Response.OrderResponse;
import com.example.shoppingmallServer.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/insert")
    public ResponseEntity<String> orderInsert(@RequestBody OrderDto orderDto) {
        return orderService.orderInsert(orderDto);
    }
    @PostMapping("/remove")
    public ResponseEntity<String> orderRemove(@RequestParam("orderDetailKey") int key) {
        return orderService.remove(key);
    }
    @GetMapping("/findAllById")
    public ResponseEntity<List<OrderResponse>> findOrder(@RequestParam("key") int memberKey) {
        return orderService.findAllById(memberKey);
    }
}
