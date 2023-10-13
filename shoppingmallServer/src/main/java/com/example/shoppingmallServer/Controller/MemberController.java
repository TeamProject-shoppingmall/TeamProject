package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.KakaoDto;
import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Dto.MemberLoginDto;
import com.example.shoppingmallServer.Dto.TokenDto;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
        log.info("Controller regularJoin");
        return memberService.regularJoin(memberDto);
    }
    @GetMapping("/kakaoLogin")
    public ResponseEntity<KakaoDto> kakaoLogin(@RequestParam("code") String code) throws Exception {
        log.info("Controller kakaoLogin");
        String kakaoAccessToken = memberService.getKakaoAccessToken(code);
        return memberService.receiveKakaoUser(kakaoAccessToken);
    }

    @PostMapping("/googleLogin")
    public String googleLogin() {
        log.info("Controller googleLogin");
        return "home";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        log.info("controller login");
        ResponseEntity<String> login = memberService.login(memberLoginDto, response);
        return login;
    }

    @PutMapping("/modify")
    public ResponseEntity<String> memberModify(@RequestHeader("Authorization") String accessToken, @RequestBody MemberDto memberDto) throws Exception {
        log.info("controller modify");
        return memberService.modify(accessToken, memberDto);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> remove(@RequestHeader("Authorization") String accessToken) {
        return memberService.remove(accessToken);
    }
}
