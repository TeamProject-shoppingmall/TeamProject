package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private int cartKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key")
    private Member memeberKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_key")
    private Item itemKey;

    private int cartCount;
}
