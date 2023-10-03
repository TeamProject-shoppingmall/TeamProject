package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Dto.ItemDto;
import com.example.shoppingmallServer.Enum.CategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_key")
    private int itemKey;

    @Column(name = "category_type")
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryType;

    private String itemName;
    private String itemPath;
    private int itemPrice;
    private int itemCount;

    public Item(FileDto fileDto, String url, String category) {
        Item item = new Item();
        this.itemName = fileDto.getItemName();
        this.itemPath = url;
        this.itemPrice = fileDto.getItemPrice();
        this.itemCount = fileDto.getItemCount();
        this.categoryType = CategoryEnum.valueOf(category);
    }
    public Item() {

    }

    public static Item createItem(FileDto fileDto, String url, String category) {
        return new Item(fileDto, url, category);
    }

    public void modifyItem(FileDto fileDto, String filePath) {
        this.itemName = fileDto.getItemName();
        this.itemCount = fileDto.getItemCount();
        this.itemPrice = fileDto.getItemPrice();
        this.itemPath = filePath;
        this.categoryType = CategoryEnum.valueOf(fileDto.getCategory());
    }
    public void modifyItem(FileDto fileDto) {
        this.itemName = fileDto.getItemName();
        this.itemCount = fileDto.getItemCount();
        this.itemPrice = fileDto.getItemPrice();
        this.categoryType = CategoryEnum.valueOf(fileDto.getCategory());
    }
}

