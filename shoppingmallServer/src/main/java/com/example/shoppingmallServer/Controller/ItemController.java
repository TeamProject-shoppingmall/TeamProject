package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Response.ImageResponse;
import com.example.shoppingmallServer.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart FileDto fileDto ,@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return itemService.uploadImage(fileDto, multipartFile);
    }

    @GetMapping("/load")
    public ResponseEntity<List<ImageResponse>> loadImage(@RequestParam("category") String category) throws IOException {
        return itemService.findAllByCategory(category);
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
