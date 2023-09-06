package com.example.shoppingmallServer.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager entityManager;
    public boolean uploadImage(byte[] imageBytes) {
        try {
            entityManager.persist(imageBytes);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
