package com.example.shoppingmallServer.Dto;

import com.example.shoppingmallServer.Entity.Answer;
import com.example.shoppingmallServer.Entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FAQDto {
    private String faqTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime faqDate;
    private String faqContent;
}