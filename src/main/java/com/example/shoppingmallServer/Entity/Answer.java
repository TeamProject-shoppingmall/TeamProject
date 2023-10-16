package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;

@Entity
public class Answer {
    @Id @GeneratedValue
    @Column(name = "answer_key")
    private int answerKey;

    private String adminId;

    private String answerText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAQ_key")
    private FAQ faq;
}
