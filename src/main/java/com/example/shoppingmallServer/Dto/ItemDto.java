package com.example.shoppingmallServer.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
    private int itemKey;
    private int itemCount;
    private int itemPrice;
    private String itemName;
}
