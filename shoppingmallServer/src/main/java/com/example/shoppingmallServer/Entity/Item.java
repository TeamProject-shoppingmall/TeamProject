package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Dto.ItemDto;
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
    private String itemPath;
    private int itemPrice;
    private int itemCount;

    public Item(ItemDto itemDto) {
        Item item = new Item();
        this.itemName = itemDto.getItemName();
        this.itemPath = itemDto.getItemPath();
        this.itemPrice = itemDto.getItemPrice();
        this.itemCount = itemDto.getItemCount();
    }
    public Item() {

    }

    public static Item createItem(ItemDto itemDto) {
        return new Item(itemDto);
    }
}
