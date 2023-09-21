package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.FailedRemoveException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<String> modifyMember(Member updateMember) {
        try {
            em.merge(updateMember);
            return new ResponseEntity<>("회원 수정을 성공했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedRemoveException("회원정보 수정이 실패했습니다.");
        }
    }

    public ResponseEntity<String> remove(Member member) {
        try {
            em.remove(member);
            return new ResponseEntity<>("회원 삭제를 성공했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedRemoveException("회원 삭제에 실패했습니다.");
        }
    }
}
