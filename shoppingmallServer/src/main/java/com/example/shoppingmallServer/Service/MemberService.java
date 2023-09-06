package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> regularJoin(Member member) {
        if (validateDuplicateMember(member.getMemberId())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("중복된 아이디입니다.");
        }
        if (memberRepository.regularJoin(member)) {
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public Member findViewById(String userId) {
        return memberRepository.findViewById(userId);
    }

    @Transactional
    public ResponseEntity<String> modify(String memberId, MemberDto memberDto) {
        Member searchMember = memberRepository.findViewById(memberId);
        try {
            searchMember.modifyMember(memberDto);
            memberRepository.modifyMember(searchMember);
            return ResponseEntity.ok("회원정보 수정에 성공했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 수정에 실패했습니다.");
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

    private boolean validateDuplicateMember(String memberId) {
        Member findMembers = memberRepository.findViewById(memberId);
        return findMembers != null;
    }
}
