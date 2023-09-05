package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController{
    private final MemberService memberService;

    @PostMapping("/regularJoin")
    public ResponseEntity<String> regularJoin(@RequestBody MemberDto memberDto) {
        log.info("Controller regularPost");
        Member member = Member.createMember(memberDto);

        if (memberService.regularJoin(member)) {
            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미 존재하는 회원입니다.");
        }
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
        return "True";
    }

    @PutMapping("/modify")
    public String memberModify(@RequestParam("userId") String userId, @RequestBody MemberDto memberDto) throws Exception {
        Member searchMember = memberService.findViewById(userId);
        log.info(searchMember.toString());
        try {
            searchMember.modifyMember(memberDto);
            memberService.modify(searchMember);
        } catch (Exception e) {
            throw new Exception("수정 정보에 오류가 발생했습니다.");
        }
        return "success";
    }
}
