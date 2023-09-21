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
import org.springframework.security.crypto.password.PasswordEncoder;
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
        return memberService.regularJoin(memberDto);
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
    public ResponseEntity<String> login(@RequestParam("memberId") String memberId, @RequestParam("memberPw") String memberPw) {
        log.info("controller login");
        return memberService.login(memberId, memberPw);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> memberModify(@RequestParam("memberId") String memberId, @RequestBody MemberDto memberDto) throws Exception {
        log.info("controller modify");
        return memberService.modify(memberId, memberDto);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> remove(@RequestParam("memberKey") int userKey) {
        return memberService.remove(userKey);
    }
}
