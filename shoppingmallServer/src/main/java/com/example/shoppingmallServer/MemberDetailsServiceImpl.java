package com.example.shoppingmallServer;

import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.example.shoppingmallServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public MemberDetailsImpl loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByUserId(id);
        if (member == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }
        MemberDetailsImpl memberDetails = new MemberDetailsImpl();
        memberDetails.setMember(member);

        return memberDetails;
    }
}
