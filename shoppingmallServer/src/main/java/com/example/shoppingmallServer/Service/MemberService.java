package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public boolean regularJoin(Member member) {
        if (validateDuplicateMember(member.getMemberId())) {
            return false; // 중복 회원일 경우 false 반환
        }
        memberRepository.regularJoin(member);
        return true;
    }

    @Transactional
    public Member findViewById(String userId) {
        return memberRepository.findViewById(userId);
    }

    @Transactional
    public void modify(Member modifyMember) {
        memberRepository.modifyMember(modifyMember);
    }

    private boolean validateDuplicateMember(String memberId) {
        Member findMembers = memberRepository.findViewById(memberId);
        return findMembers != null;
    }
}
