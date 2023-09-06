package com.example.shoppingmallServer.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class FileDto {
    private int itemCount;
    private int itemPrice;
    private String itemName;
}
