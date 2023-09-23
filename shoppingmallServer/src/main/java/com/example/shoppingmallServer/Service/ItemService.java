package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FileDto;
import com.example.shoppingmallServer.Dto.ItemDto;
import com.example.shoppingmallServer.Entity.Cart;
import com.example.shoppingmallServer.Entity.Item;
import com.example.shoppingmallServer.Exception.*;
import com.example.shoppingmallServer.Repository.ItemRepository;
import com.example.shoppingmallServer.Response.ImageResponse;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Value("${file.path}")
    private String itemPath;

    private int itemCount;
    private int itemPrice;
    private String itemName;
    private String category;
    @Transactional
    public ResponseEntity<String> uploadItem(FileDto fileDto, MultipartFile multipartFile) throws IOException {
        String filePath = Paths.get(itemPath, multipartFile.getOriginalFilename()).toString();
        File file = new File(filePath);

        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            throw new DuplicateFileException("이미 존재하는 파일입니다.");
        }

        Item item = Item.createItem(fileDto, filePath, fileDto.getCategory());

        try{
            itemRepository.uploadItem(item);
            return new ResponseEntity<>("상품이 등록되었습니다.", HttpStatus.OK);
        } catch (Exception e){
            throw new FailedUploadItem("상품 등록에 실패했습니다.");
        }
    }

    public ResponseEntity<List<ImageResponse>> findAllByCategory(String category) throws IOException {
        List<Item> item = itemRepository.findAllByCategory(category);
        if (item.isEmpty()) {
            throw new EmptyCategoryItem("등록된 상품이 없습니다.");
        }
        List<ImageResponse> imageAll = new ArrayList<>();

        for (Item item1: item) {
            byte[] image = getImageDataFromPath(item1.getItemPath());
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
            throw new FileNotFoundException("상품 이미지가 존재하지 않습니다.");
        }
    }

    public ResponseEntity<ImageResponse> findOneById(int itemKey) throws IOException {
        Item item = itemRepository.findOneById(itemKey);
        if (item == null) {
            throw new EmptyCategoryItem("등록된 상품이 없습니다.");
        }
        byte[] imageDataFromPath = getImageDataFromPath(item.getItemPath());

        return new ResponseEntity<>(ImageResponse.findImageOne(item.getItemName(), item.getItemPrice(), imageDataFromPath), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<String> removeItem(int itemKey) throws FileNotFoundException {
        log.info("itemService");
        Item oneById = itemRepository.findOneById(itemKey);
        if (oneById == null) {
            throw new NotFoundException("장바구니 정보가 존재하지 않습니다.");
        }
        File file = new File(oneById.getItemPath());
        file.delete();
        return itemRepository.removeItem(oneById);
    }

    @Transactional
    public ResponseEntity<String> modifyItem(FileDto fileDto, MultipartFile multipartFile, int itemKey) throws IOException {
        Item oneById = itemRepository.findOneById(itemKey);
        if (oneById == null) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }
        String filePath = Paths.get(itemPath, multipartFile.getOriginalFilename()).toString();

        if (oneById.getItemPath() != filePath) {
            File removeFile = new File(oneById.getItemPath());
            removeFile.delete();
            File insertFile = new File(filePath);
            multipartFile.transferTo(insertFile);
            oneById.modifyItem(fileDto, filePath);
        } else {
            oneById.modifyItem(fileDto);
        }
        return itemRepository.modifyItem(oneById);
    }
}
