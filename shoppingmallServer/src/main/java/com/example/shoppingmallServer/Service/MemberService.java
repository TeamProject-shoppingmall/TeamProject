package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.MemberDto;
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
    public void regularJoin(Member member) {
        memberRepository.regularJoin(member);
    }
}
