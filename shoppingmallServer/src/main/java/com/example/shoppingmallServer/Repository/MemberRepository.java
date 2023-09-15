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

    public boolean regularJoin(Member member) {
        try {
            em.persist(member);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Member findOneById(int memberId) {
        return em.find(Member.class, memberId);
    }

    public Member findOneByUserId(String userId) {
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
