package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            String filePath = Paths.get(itemPath, multipartFile.getOriginalFilename()).toString();
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
        System.out.println(item);
        for (Item item1: item) {
            byte[] image = getImageDataFromPath(item1.getItemPath());
//            String base64Image = Base64.getEncoder().encodeToString(image);
//            System.out.println(base64Image.length());
            imageAll.add(ImageResponse.findImageOne(item1.getItemName(), item1.getItemPrice(), image));
        }
        return new ResponseEntity<>(imageAll, HttpStatus.OK);
    }

    public byte[] getImageDataFromPath(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        Resource imageResource = new FileSystemResource((path.toFile()));

        if (imageResource.exists()) {
            return imageResource.getContentAsByteArray();
        } else {
            throw new FileNotFoundException("Image file not found: " + imagePath);
        }
    }

    public ResponseEntity<ImageResponse> findByItemName(String name) {
        try {
            Item item = itemRepository.findByItemName(name);
            byte[] imageDataFromPath = getImageDataFromPath(item.getItemPath());
            return new ResponseEntity<>(ImageResponse.findImageOne(item.getItemName(), item.getItemPrice(), imageDataFromPath), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ImageResponse.getErrorMessage("이미지를 조회할 수 없습니다.") ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
