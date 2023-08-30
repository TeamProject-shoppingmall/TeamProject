package com.example.shoppingmallServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Admin {
    @Id
    @GeneratedValue
    @Column(name = "admin_key")
    private int adminKey;
    private String adminId;
    private String adminPw;
}
