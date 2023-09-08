package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Dto.ItemDto;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Enum.CategoryEnum;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Response.ImageResponse;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Value("${file.path}")
    private String itemPath;

    @Transactional
    public ResponseEntity<String> uploadImage(FileDto fileDto, MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String filePath = Paths.get(itemPath, fileDto.getItemName()).toString();
            File file = new File(filePath);
            multipartFile.transferTo(file);
            Item item = Item.createItem(fileDto, filePath, fileDto.getCategory());
            if (itemRepository.uploadImage(item)) {
                return ResponseEntity.ok("이미지 저장에 성공했습니다.");
            } else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드에 실패했습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일이 전송되지 않았습니다.");
        }
    }

    public ResponseEntity<List<ImageResponse>> findAllByCategory(String category) throws IOException {
        List<Item> item = itemRepository.findAllByCategory(category);
        List<ImageResponse> imageAll = new ArrayList<>();
//        List<ImageResponse> imageAll = new ArrayList<>();
        System.out.println(item);
        for (Item item1: item) {
            System.out.println(item1);
            imageAll.add(ImageResponse.findImageOne(item1.getItemName(), item1.getItemPrice(), item1.getItemPath()));
        }

        return new ResponseEntity<>(imageAll, HttpStatus.OK);
    }
}
