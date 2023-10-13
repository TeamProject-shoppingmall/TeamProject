package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.KakaoDto;
import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Dto.MemberLoginDto;
import com.example.shoppingmallServer.Dto.TokenDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.RefreshToken;
import com.example.shoppingmallServer.Exception.*;
import com.example.shoppingmallServer.JWT.JwtTokenProvider;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.TokenRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    @Value("${kakao-client-id}")
    private String KAKAO_CLIENT_KEY;
    String access_Token="";
    String refresh_Token ="";
    String authorizeURL = "https://kauth.kakao.com";
    String resourceURL = "https://kapi.kakao.com";

    @Transactional
    public ResponseEntity<String> regularJoin(MemberDto memberDto) {
        if (StringUtils.isBlank(memberDto.getMemberId())) {
            throw new EmptyValueException("아이디가 입력되지 않았습니다.");
        }

        if (StringUtils.isBlank(memberDto.getMemberPw())) {
            throw new EmptyValueException("비밀번호가 입력되지 않았습니다.");
        }

        if (memberRepository.findOneByUserId(memberDto.getMemberId()) != null) {
            throw new DuplicateUserException("중복된 아이디입니다.");
        }

        memberDto.setMemberPw(passwordEncoder.encode(memberDto.getMemberPw()));
        Member member = Member.createMember(memberDto);

        try {
            memberRepository.regularJoin(member);
            return new ResponseEntity<>("회원가입이 완료됐습니다", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedJoinException("회원가입이 실패했습니다.");
        }
    }

    public String getKakaoAccessToken(String code) {
        if (code == null) {
            throw new EmptyValueException("Access Token이 입력되지 않았습니다.");
        }

        try {
            WebClient webClient = WebClient.builder().baseUrl(authorizeURL).build();
            String token = webClient.get()
                    .uri("/oauth/token?grant_type=authorization_code&client_id=" + KAKAO_CLIENT_KEY
                            + "&redirect_uri=http://localhost:8080/member/kakaoLogin"
                            + "&code=" + code)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // JSON 파싱을 통해 Access Token 및 Refresh Token 추출
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(token);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            return access_Token;
        } catch (Exception e) {
            throw new FailedLoginException("토큰을 가져오는데 실패헀습니다.");
        }
    }

    public ResponseEntity<KakaoDto> receiveKakaoUser(String token) throws Exception {
        if(token == null) throw new Exception("failed get Id_Token");
        try {
            WebClient webClient = WebClient.builder().baseUrl(resourceURL).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();

            String resource = webClient.get()
                    .uri("/v2/user/me")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(resource);

            Long id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            KakaoDto kakaoDto = new KakaoDto(id, email, nickname);

            return new ResponseEntity<>(kakaoDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("API call failed");
        }
    }

    @Transactional
    public ResponseEntity<String> modify(String accessToken, MemberDto memberDto) {
        if (StringUtils.isBlank(memberDto.getMemberId())) {
            throw new EmptyValueException("아이디가 입력되지 않았습니다.");
        }

        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member oneByUserId = memberRepository.findOneByUserId(idFromToken);

        if (oneByUserId == null) {
            throw new NotFoundException("회원 정보가 없습니다.");
        }

        oneByUserId.modifyMember(memberDto);

        return memberRepository.modifyMember(oneByUserId);
    }

    @Transactional
    public ResponseEntity<String> login(MemberLoginDto memberLoginDto, HttpServletResponse response) {
        if (StringUtils.isBlank(memberLoginDto.getMemberId())) {
            throw new EmptyValueException("아이디가 입력되지 않았습니다.");
        }

        if (StringUtils.isBlank(memberLoginDto.getMemberPw())) {
            throw new EmptyValueException("비밀번호가 입력되지 않았습니다.");
        }

        Member viewById = memberRepository.findOneByUserId(memberLoginDto.getMemberId());
        if (viewById == null) {
            throw new NotFoundException("아이디가 존재하지 않습니다.");
        }

        if (passwordEncoder.matches(memberLoginDto.getMemberPw(), viewById.getMemberPw())) {
            TokenDto tokenDto = jwtTokenProvider.createAllToken(memberLoginDto.getMemberId());
            Optional<RefreshToken> refreshToken = tokenRepository.findByMemberId(viewById.getMemberId());

            // 있다면 새토큰 발급후 업데이트
            // 없다면 새로 만들고 디비 저장
            if(refreshToken.isPresent()) {
                tokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
            }else {
                RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), memberLoginDto.getMemberId());
                tokenRepository.save(newToken);
            }

            Cookie cookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
            cookie.setMaxAge((int) (2 * 60 * 1000L));
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return new ResponseEntity<>(tokenDto.getAccessToken(), HttpStatus.OK);
        } else {
            throw new PwDoesNotMatched("비밀번호가 일치하지 않습니다.");
        }
    }
    @Transactional
    public ResponseEntity<String> remove(String accessToken) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member oneByUserId = memberRepository.findOneByUserId(idFromToken);
        if (oneByUserId == null) {
            throw new NotFoundException("회원 정보가 없습니다.");
        }
        return memberRepository.remove(oneByUserId);
    }
}
