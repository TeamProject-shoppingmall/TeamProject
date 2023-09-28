package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Entity.RefreshToken;
import com.example.shoppingmallServer.Repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public Optional<RefreshToken> findByMemberId(String id) {
        return tokenRepository.findByMemberId(id);
    }
}
