package com.example.shoppingmallServer.Response;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Data
public class ImageResponse {
    private int itemKey;
    private String imageName;
    private int imagePrice;
    private String imagePath;

    public ImageResponse(int itemKey, String imageName, int imagePrice, String imagePath) throws FileNotFoundException {
        this.itemKey = itemKey;
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.imagePath = imagePath;
    }

    public static ImageResponse findImageOne(int itemKey, String imageName, int imagePrice, String imagePath) throws FileNotFoundException {
        return new ImageResponse(itemKey, imageName,imagePrice,imagePath);
    }
}
