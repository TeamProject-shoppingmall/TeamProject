package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController{
    private MemberService memberService;

    @PostMapping("/regularJoin")
    public String regularJoin(@RequestBody MemberDto memberDto) {
        Member member = Member.createMember(memberDto);
        memberService.regularJoin(member);
        return "";
    }

    @PostMapping("/kakaoJoin")
    public String kakaoJoin() {
        return "home";
    }

    @PostMapping("/googleJoin")
    public String googleJoin() {
        return "home";
    }

    @PostMapping("/login")
    public String login() {
        return "home";
    }

    @PutMapping("/modify")
    public String memberModify() {
        return "home";
    }
}
