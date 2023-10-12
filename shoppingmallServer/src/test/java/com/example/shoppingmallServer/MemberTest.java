package com.example.shoppingmallServer;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Enum.GenderEnum;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 일반회원테스트() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId("test");
        memberDto.setMemberPw("1234");
        memberDto.setMemberGender(GenderEnum.male);
        memberDto.setKakaoMember(MemberEnum.N);
        memberDto.setGoogleMember(MemberEnum.N);
        memberDto.setRegularMember(MemberEnum.Y);
        memberDto.setMemberEmail("test@gmail.com");
        memberDto.setMemberName("test");
        memberDto.setMemberPhone("010-1111-1111");
        memberDto.setZipcode("54321");
        memberDto.setAddr("인천광역시 남구");
        memberDto.setAddrDetail("인하대");

        memberService.regularJoin(memberDto);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 정보수정테스트() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId("modify");
        memberDto.setMemberPw(passwordEncoder.encode("1234"));
        memberDto.setMemberGender(GenderEnum.male);
        memberDto.setKakaoMember(MemberEnum.N);
        memberDto.setGoogleMember(MemberEnum.N);
        memberDto.setRegularMember(MemberEnum.Y);
        memberDto.setMemberEmail("modify@gmail.com");
        memberDto.setMemberName("modify");
        memberDto.setMemberPhone("010-1111-1111");
        memberDto.setZipcode("54321");
        memberDto.setAddr("인천광역시 남구");
        memberDto.setAddrDetail("인하대");

        memberService.modify("modify", memberDto);
    }
}
