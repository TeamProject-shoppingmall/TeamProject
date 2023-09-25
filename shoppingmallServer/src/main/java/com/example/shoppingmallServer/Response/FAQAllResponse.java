package com.example.shoppingmallServer.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FAQAllResponse {
    private int faqKey;
    private String faqTitle;
    private LocalDateTime faqDate;
}
