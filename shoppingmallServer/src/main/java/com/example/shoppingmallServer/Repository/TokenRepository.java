package com.example.shoppingmallServer.Repository;

import com.example.shoppingmallServer.Entity.RefreshToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.example.shoppingmallServer.Entity.QRefreshToken.refreshToken1;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final EntityManager em;

    public Optional<RefreshToken> findByMemberId(String id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Optional<RefreshToken> refreshToken = Optional.ofNullable(queryFactory.selectFrom(refreshToken1)
                .where(refreshToken1.memberId.eq(id)).fetchOne());
        return refreshToken;
    }

    public void save(RefreshToken token) {
        em.persist(token);
    }
}
