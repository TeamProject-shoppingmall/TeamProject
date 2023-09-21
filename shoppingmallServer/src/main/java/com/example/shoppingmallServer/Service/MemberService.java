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

    public ResponseEntity<String> login(String memberId, String memberPw) {
        if (StringUtils.isBlank(memberId)) {
            throw new EmptyValueException("아이디가 입력되지 않았습니다.");
        }

        if (StringUtils.isBlank(memberPw)) {
            throw new EmptyValueException("비밀번호가 입력되지 않았습니다.");
        }

        Member viewById = memberRepository.findOneByUserId(memberId);
        if (viewById == null) {
            throw new NotFoundException("아이디가 존재하지 않습니다.");
        }

        if (passwordEncoder.matches(memberPw, viewById.getMemberPw())) {
            return new ResponseEntity<>("로그인이 성공했습니다.", HttpStatus.OK);
        } else {
            throw new PwDoesNotMatch("비밀번호가 일치하지 않습니다.");
        }
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
