package com.example.fashioncommuni.board.DTO.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostImageUploadDTO {
    private List<MultipartFile> files;
}
