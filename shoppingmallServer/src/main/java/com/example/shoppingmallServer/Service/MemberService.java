package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Dto.MemberLoginDto;
import com.example.shoppingmallServer.Dto.TokenDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Entity.RefreshToken;
import com.example.shoppingmallServer.Exception.*;
import com.example.shoppingmallServer.JWT.JwtTokenProvider;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Repository.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    @Transactional
    public ResponseEntity<String> regularJoin(MemberDto memberDto) {
//        String chkId = Optional.ofNullable(memberDto.getMemberId()).orElseThrow(EmptyValueExistException::new);
//        String chkPw = Optional.ofNullable(memberDto.getMemberPw()).orElseThrow(EmptyValueExistException::new);
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

    @Transactional
    public ResponseEntity<String> modify(String memberId, MemberDto memberDto) {
        if (StringUtils.isBlank(memberDto.getMemberId())) {
            throw new EmptyValueException("아이디가 입력되지 않았습니다.");
        }

        Member searchMember = memberRepository.findOneByUserId(memberId);

        if (searchMember == null) {
            throw new NotFoundException("아이디가 존재하지 않습니다.");
        }

        searchMember.modifyMember(memberDto);

        return memberRepository.modifyMember(searchMember);
    }

    @Transactional
    public ResponseEntity<TokenDto> login(MemberLoginDto memberLoginDto, HttpServletResponse response) {
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

            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } else {
            throw new PwDoesNotMatched("비밀번호가 일치하지 않습니다.");
        }
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(jwtTokenProvider.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(jwtTokenProvider.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
    @Transactional
    public ResponseEntity<String> remove(int memberKey) {
        Member oneById = memberRepository.findOneById(memberKey);
        if (oneById == null) {
            throw new NotFoundException("회원 정보가 없습니다.");
        }
        return memberRepository.remove(oneById);
    }
}
