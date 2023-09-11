package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.*;
import com.example.shoppingmallServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public ResponseEntity<String> regularJoin(MemberDto memberDto) {
//        String chkId = Optional.ofNullable(memberDto.getMemberId()).orElseThrow(EmptyValueExistException::new);
//        String chkPw = Optional.ofNullable(memberDto.getMemberPw()).orElseThrow(EmptyValueExistException::new);
        if (StringUtils.isBlank(memberDto.getMemberId())) {
            throw new EmptyValueExistException();
        }

        if (StringUtils.isBlank(memberDto.getMemberPw())) {
            throw new EmptyValueExistException();
        }

        if (memberRepository.findViewById(memberDto.getMemberId()) != null) {
            throw new DuplicateUserException();
        }

        memberDto.setMemberPw(passwordEncoder.encode(memberDto.getMemberPw()));
        Member member = Member.createMember(memberDto);

        try {
            memberRepository.regularJoin(member);
            return new ResponseEntity<>("회원가입이 완료됐습니다", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedJoinException();
        }
    }

    @Transactional
    public ResponseEntity<String> modify(String memberId, MemberDto memberDto) {
        Member searchMember = memberRepository.findViewById(memberId);

        if (searchMember == null) {
            throw new NotFoundUserException();
        }

        searchMember.modifyMember(memberDto);

        try {
            memberRepository.modifyMember(searchMember);
            return new ResponseEntity<>("회원가입이 완료됐습니다", HttpStatus.OK);
        } catch (Exception e) {
            throw new FailedModifyException();
        }
    }

    public ResponseEntity<String> login(String memberId, String memberPw) {
        Member viewById = memberRepository.findViewById(memberId);
        if (passwordEncoder.matches(memberPw, viewById.getMemberPw())) {
            return ResponseEntity.ok("로그인에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }
}
