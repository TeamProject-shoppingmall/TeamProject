package com.example.shoppingmallServer.Response;

import com.example.shoppingmallServer.Entity.Item;
import com.querydsl.core.Tuple;
import lombok.Data;

@Data
public class CartResponse {
    private int cartKey;
    private String itemTitle;
    private int itemPrice;
    private String itemImage;
    private int cartCount;
    private int totalPrice;

    public CartResponse(Tuple cart) {
        this.cartKey = cart.get(0, Integer.class);
        this.itemTitle = cart.get(2, String.class);
        this.itemPrice = cart.get(3, Integer.class);
        this.itemImage = cart.get(4, String.class);
        this.cartCount = cart.get(1, Integer.class);
        this.totalPrice = cart.get(5, Integer.class);
    }
}
