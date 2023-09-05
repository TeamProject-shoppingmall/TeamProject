package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.shoppingmallServer.Entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void regularJoin(Member member) {
        em.persist(member);
    }

    public Member findViewById(String userId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.selectFrom(member)
                .where(member.memberId.eq(userId))
                .fetchOne();
        //        return em.find(Member.class, userId);
    }

    public void modifyMember(Member updateMember) {
        em.merge(updateMember);
    }
}
