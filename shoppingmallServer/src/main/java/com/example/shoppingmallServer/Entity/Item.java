package com.example.shoppingmallServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_key")
    private int itemKey;

    private String itemName;
    private String itemImg;
    private String itemDiscription;
    private int itemPrice;
    private int itemCount;
}
