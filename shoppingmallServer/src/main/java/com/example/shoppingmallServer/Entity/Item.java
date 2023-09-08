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

    public Item(FileDto fileDto, String filePath, String category) {
        Item item = new Item();
        this.itemName = fileDto.getItemName();
        this.itemPath = filePath;
        this.itemPrice = fileDto.getItemPrice();
        this.itemCount = fileDto.getItemCount();
        this.categoryType = CategoryEnum.valueOf(category);
    }
    public Item() {

    }

    public static Item createItem(FileDto fileDto, String filePath, String category) {
        return new Item(fileDto, filePath, category);
    }
}
