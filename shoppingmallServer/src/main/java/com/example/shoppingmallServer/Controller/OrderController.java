package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.OrderDto;
import com.example.shoppingmallServer.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/insert")
    public ResponseEntity<String> orderInsert(@RequestBody OrderDto orderDto) {
        return orderService.orderInsert(orderDto);
    }
    @PostMapping("/cancel")
    public String orderCancel() {
        return "home";
    }

    @PostMapping("/modify")
    public String orderModify() {
        return "home";
    }

    @GetMapping("/find")
    public String findOrder() {
        return "home";
    }
}
