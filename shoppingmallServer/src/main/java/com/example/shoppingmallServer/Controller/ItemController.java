package com.example.shoppingmallServer.Controller;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Dto.ItemDto;
import com.example.shoppingmallServer.Response.ImageResponse;
import com.example.shoppingmallServer.Service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadItem(@RequestPart FileDto fileDto, @RequestPart("file") MultipartFile multipartFile) throws IOException {
        return itemService.uploadItem(fileDto, multipartFile);
    }

    @GetMapping("/findAllByCategory")
    public ResponseEntity<List<ImageResponse>> findAllByCategory(@RequestParam("category") String category) throws IOException {
        return itemService.findAllByCategory(category);
    }

    @GetMapping("/findOneByImageName")
    public ResponseEntity<ImageResponse> findOneByImageName(@RequestParam("imageName") String imageName) throws IOException {
        return itemService.findOneByImageName(imageName);
    }

    @PutMapping("modify")
    public ResponseEntity<String> modify(@RequestPart FileDto fileDto, @RequestPart("file") MultipartFile multipartFile, @RequestParam("itemKey") int itemKey) throws IOException {
        return itemService.modifyItem(fileDto, multipartFile, itemKey);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeImage(@RequestParam("itemKey") int itemKey) throws FileNotFoundException {
        log.info("remove Controller");
        return itemService.removeItem(itemKey);
    }
}
