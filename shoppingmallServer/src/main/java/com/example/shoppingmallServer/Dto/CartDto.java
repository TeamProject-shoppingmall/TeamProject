package com.example.shoppingmallServer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class CartDto {
    private int memberKey;

    private int itemKey;

    private int cartCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime cartDate;
}