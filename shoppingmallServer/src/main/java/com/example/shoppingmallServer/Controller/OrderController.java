package com.example.shoppingmallServer.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/insert")
    public String orderInsert() {
        return "home";
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
