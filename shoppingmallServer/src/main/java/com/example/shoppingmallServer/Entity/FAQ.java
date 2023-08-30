package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class FAQ {
    @Id
    @GeneratedValue
    private int faqKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key")
    private Member memberKey;
    private String faqTitle;
    private Date faqDate;
    private String faqContent;
}
