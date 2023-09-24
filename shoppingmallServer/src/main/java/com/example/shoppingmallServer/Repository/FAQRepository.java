package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.FAQ;
import com.example.shoppingmallServer.Exception.FailedInsertException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FAQRepository {
    private final EntityManager em;

    public ResponseEntity<String> insert(FAQ faq) {
        try {
            em.persist(faq);
            return new ResponseEntity<>("문의사항 등록에 성공했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedInsertException("문의사항 등록에 실패했습니다.");
        }
    }
}
