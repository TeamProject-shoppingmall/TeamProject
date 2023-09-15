package com.example.shoppingmallServer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class CartDto {
    private int memberKey;

    private int itemKey;

    private int cartCount;

    private ZonedDateTime cartDate;
}