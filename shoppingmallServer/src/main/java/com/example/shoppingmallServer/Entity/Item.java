package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_key")
    private int itemKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_key")
    private Category categoryKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_key")
    private Admin adminKey;

    private String itemName;
    private String itemImg;
    private String itemDiscription;
    private int itemPrice;
    private int itemCount;
}
