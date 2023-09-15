package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.CartDto;
import com.example.shoppingmallServer.Response.CartResponse;
import com.example.shoppingmallServer.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody CartDto cartDto) {
        return cartService.cartInsert(cartDto);
    }

    @GetMapping("/findAllById")
    public ResponseEntity<List<CartResponse>> findOneById(@RequestParam("key") int key) throws IOException {
        return cartService.findOneById(key);
    }

}
