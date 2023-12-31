package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Dto.CartDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Getter
public class Cart {
    @Id
    @GeneratedValue
    private int cartKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key")
    private Member memberKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_key")
    private Item itemKey;

    private int cartCount;

    private LocalDateTime cartDate;

    public Cart(Member member, Item item, CartDto cartDto) {
        this.memberKey = member;
        this.itemKey = item;
        this.cartCount = cartDto.getCartCount();
        this.cartDate = cartDto.getCartDate();
    }

    public Cart() {

    }

    public static Cart createCart(Member member, Item item, CartDto cartDto) {
        return new Cart(member, item, cartDto);
    }
}
