package com.example.shoppingmallServer.Response;

import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Data
public class ImageResponse {
    private String imageName;
    private int imagePrice;
    private String imagePath;
    private String errorMessage;

    public ImageResponse(String imageName, int imagePrice, String imagePath) throws FileNotFoundException {
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.imagePath = imagePath;
        this.errorMessage = null;
    }

    public ImageResponse(String errorMessage) {
        this.imageName = null;
        this.imagePrice = 0;
        this.imagePath = null;
        this.errorMessage = errorMessage;
    }

    public static ImageResponse findImageOne(String imageName, int imagePrice, String imagePath) throws FileNotFoundException {
        return new ImageResponse(imageName,imagePrice,imagePath);
    }

    public static ImageResponse getErrorMessage(String errorMessage) {
        return new ImageResponse(errorMessage);
    }
}
