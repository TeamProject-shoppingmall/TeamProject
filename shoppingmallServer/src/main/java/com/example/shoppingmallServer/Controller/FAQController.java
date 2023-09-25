package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.FAQDto;
import com.example.shoppingmallServer.Response.FAQAllResponse;
import com.example.shoppingmallServer.Response.FAQResponse;
import com.example.shoppingmallServer.Service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/FAQ")
public class FAQController {
    private final FAQService faqService;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody FAQDto faqDto) {
        return faqService.insert(faqDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<FAQAllResponse>> findAll() {
        return faqService.findAll();
    }

    @GetMapping("/findOneById")
    public ResponseEntity<FAQResponse> findOneById(@RequestParam("faqKey") int key) {
        return faqService.findOneById(key);
    }

//    @PostMapping("/remove")
//    public ResponseEntity<String> remove(@RequestParam("faqKey") int key) {
//
//    }
}