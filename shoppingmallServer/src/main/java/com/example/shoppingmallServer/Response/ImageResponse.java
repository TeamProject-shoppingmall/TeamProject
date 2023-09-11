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
    private byte[] image;
    private String errorMessage;

    public ImageResponse(String imageName, int imagePrice, byte[] image) throws FileNotFoundException {
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.image = image;
        this.errorMessage = null;
    }

    public ImageResponse(String errorMessage) {
        this.imageName = null;
        this.imagePrice = 0;
        this.image = null;
        this.errorMessage = errorMessage;
    }

    public static ImageResponse findImageOne(String imageName, int imagePrice, byte[] image) throws FileNotFoundException {
        return new ImageResponse(imageName,imagePrice,image);
    }

    public static ImageResponse getErrorMessage(String errorMessage) {
        return new ImageResponse(errorMessage);
    }
}
