package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Dto.ItemDto;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Value("${file.path}")
    private String itemPath;

    @Transactional
    public ResponseEntity<String> uploadImage(FileDto fileDto, MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String saveFilePath = itemPath;
            String saveFileName = fileDto.getItemName();
            String filePath = Paths.get(saveFilePath, saveFileName).toString();
            File file = new File(filePath);
            multipartFile.transferTo(file);
            return ResponseEntity.ok("이미지 저장에 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드에 실패했습니다.");
        }
    }
}
