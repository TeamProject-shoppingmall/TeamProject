package com.example.shoppingmallServer.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    @GetMapping("/best")
    public String bestItem() {
        return "home";
    }

    @GetMapping("/top")

    public String topItem() {
        return "home";
    }

    @GetMapping("/shirt")
    public String shirtItem() {
        return "home";
    }

    @GetMapping("/bottom")
    public String bottomItem() {
        return "home";
    }

    @GetMapping("/acc")
    public String accItem() {
        return "home";
    }

    @PostMapping("/insert")
    public String insertItem() {
        return "home";
    }
}
