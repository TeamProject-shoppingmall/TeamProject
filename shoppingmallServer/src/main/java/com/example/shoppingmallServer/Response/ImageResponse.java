package com.example.shoppingmallServer.Response;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Data
public class ImageResponse {
    private String imageName;
    private int imagePrice;
    private String imagePath;

    public ImageResponse(String imageName, int imagePrice, String imagePath) throws FileNotFoundException {
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.imagePath = imagePath;
    }

    public static ImageResponse findImageOne(String imageName, int imagePrice, String imagePath) throws FileNotFoundException {
        return new ImageResponse(imageName,imagePrice,imagePath);
    }
}
