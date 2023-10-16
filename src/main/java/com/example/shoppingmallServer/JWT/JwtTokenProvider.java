package com.example.shoppingmallServer.JWT;

import com.example.shoppingmallServer.Dto.TokenDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.RefreshToken;
import com.example.shoppingmallServer.MemberDetailsImpl;
import com.example.shoppingmallServer.MemberDetailsServiceImpl;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberDetailsServiceImpl memberDetailsService;
    private final TokenRepository tokenRepository;
    // 유효시간
    private static final long ACCESS_TIME =  60 * 60 * 1000L;
    private static final long REFRESH_TIME =  2 * 60 * 60 * 1000L;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    @Value("${secret.key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) :request.getHeader(REFRESH_TOKEN);
    }

    // 토큰 생성
    public TokenDto createAllToken(String nickname) {
        return new TokenDto(createToken(nickname, "Access"), createToken(nickname, "Refresh"));
    }

    // 토큰 생성
    public String createToken(String nickname, String type) {
        Date now = new Date();
        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;
        return Jwts.builder()
                // payload 정보저장
                .setSubject(nickname)
                // 만기 시간
                .setExpiration(new Date(now.getTime() + time))
                // 발급 시간
                .setIssuedAt(now)
                // 알고리즘, 키
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // refreshToken 토큰 검증
    // db에 저장되어 있는 token과 비교
    // db에 저장한다는 것이 jwt token을 사용한다는 강점을 상쇄시킨다.
    // db 보다는 redis를 사용하는 것이 더욱 좋다. (in-memory db기 때문에 조회속도가 빠르고 주기적으로 삭제하는 기능이 기본적으로 존재합니다.)
    public Boolean refreshTokenValidation(String token) {

        // 1차 토큰 검증
        if(!validateToken(token)) return false;

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = tokenRepository.findByMemberId(getIdFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    public String getIdFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 엑세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Access_Token", accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Refresh_Token", refreshToken);
    }

    public Authentication createAuthentication(String id) {
        MemberDetailsImpl memberDetails = memberDetailsService.loadUserByUsername(id);
        // spring security 내에서 가지고 있는 객체입니다. (UsernamePasswordAuthenticationToken)
        return new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
}

}
