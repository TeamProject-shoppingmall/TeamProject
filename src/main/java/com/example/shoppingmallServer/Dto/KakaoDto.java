package com.example.shoppingmallServer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoDto {
    private Long id;
    private String email;
    private String nickname;
}
