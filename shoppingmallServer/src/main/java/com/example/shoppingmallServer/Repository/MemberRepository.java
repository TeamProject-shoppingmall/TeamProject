package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void regularJoin(Member member) {
        em.persist(member);
    }
}
