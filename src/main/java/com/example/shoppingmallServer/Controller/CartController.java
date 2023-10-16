package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.CartDto;
import com.example.shoppingmallServer.Response.CartResponse;
import com.example.shoppingmallServer.Service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody CartDto cartDto, @RequestHeader("Authorization") String accessToken) {
        log.info(accessToken);
        log.info(String.valueOf(cartDto.getItemKey()));
        log.info(String.valueOf(cartDto.getCartCount()));
        return cartService.cartInsert(cartDto, accessToken);
    }

    @GetMapping("/findAllById")
    public ResponseEntity<List<CartResponse>> findOneById(@RequestHeader("Authorization") String accessToken) throws IOException {
        return cartService.findOneById(accessToken);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam("cartKey") int cartKey, @RequestHeader("Authorization") String accessToken) {
        return cartService.remove(cartKey, accessToken);
    }

    @PostMapping("/removeAll")
    public ResponseEntity<String> removeAll(@RequestHeader("Authorization") String accessToken) {
        return cartService.removeAll(accessToken);
    }
}
