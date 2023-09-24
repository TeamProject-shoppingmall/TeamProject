package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.FAQDto;
import com.example.shoppingmallServer.Service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/FAQ")
public class FAQController {
    private final FAQService faqService;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody FAQDto faqDto) {
        return faqService.insert(faqDto);
    }
}
