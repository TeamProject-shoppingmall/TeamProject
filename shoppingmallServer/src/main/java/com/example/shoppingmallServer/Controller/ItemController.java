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

    @GetMapping("/findAllByCategory")
    public ResponseEntity<List<ImageResponse>> findAllByCategory(@RequestParam("category") String category) throws IOException {
        return itemService.findAllByCategory(category);
    }

    @GetMapping("/findByItemName")
    public ResponseEntity<ImageResponse> findByItemName(@RequestParam("itemName") String name) {
        return itemService.findByItemName(name);
    }
}
