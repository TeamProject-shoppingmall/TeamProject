package com.example.shoppingmallServer;

import com.example.shoppingmallServer.Service.CartService;
import com.example.shoppingmallServer.Service.ItemService;
import com.example.shoppingmallServer.Service.MemberService;
import com.example.shoppingmallServer.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class sampleTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
}
