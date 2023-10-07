package com.example.shoppingmallServer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class CartDto {
    private int itemKey;

    private int cartCount;

    private LocalDateTime cartDate;

    public CartDto(int itemKey, int cartCount) {
        this.itemKey = itemKey;
        this.cartCount = cartCount;
        this.cartDate = LocalDateTime.now();
    }
}