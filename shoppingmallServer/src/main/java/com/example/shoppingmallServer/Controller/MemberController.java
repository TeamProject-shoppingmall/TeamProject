package com.example.shoppingmallServer.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController{

    @PostMapping("/regularJoin")
    public String regularJoin() {
        return "home";
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
