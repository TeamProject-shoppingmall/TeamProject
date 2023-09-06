package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Dto.ItemDto;
import com.example.shoppingmallServer.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart FileDto fileDto ,@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return itemService.uploadImage(fileDto, multipartFile);
    }

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
