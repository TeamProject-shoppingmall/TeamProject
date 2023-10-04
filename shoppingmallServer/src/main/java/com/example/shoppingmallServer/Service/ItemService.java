package com.example.shoppingmallServer.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import org.apache.commons.io.IOUtils;

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
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ResponseEntity<String> uploadItem(FileDto fileDto, MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());

        String fileName = fileDto.getCategory() + "/" + file.getName();

        // S3로 이미지 업로드 putObjectRequest 파일 인스턴스를 생성해 업로드 진행 (withCannedAcl은 접근 권한 설정)
        log.info("upload S3");
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));

        if (file.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }

        String url = amazonS3Client.getUrl(bucket, fileName).toString();

        Item item = Item.createItem(fileDto, url, fileDto.getCategory());

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
            imageAll.add(ImageResponse.findImageOne(item1.getItemName(), item1.getItemPrice(), item1.getItemPath()));
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

    public ResponseEntity<ImageResponse> findOneByImageName(String imageName) throws IOException {
        Item item = itemRepository.findOneByImageName(imageName);
        if (item == null) {
            throw new EmptyCategoryItem("등록된 상품이 없습니다.");
        }

        return new ResponseEntity<>(ImageResponse.findImageOne(item.getItemName(), item.getItemPrice(), item.getItemPath()), HttpStatus.OK);
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
        String filePath = Paths.get(multipartFile.getOriginalFilename()).toString();

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
