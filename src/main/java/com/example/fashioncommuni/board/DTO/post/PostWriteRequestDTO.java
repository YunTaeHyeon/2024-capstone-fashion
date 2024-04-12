package com.example.fashioncommuni.board.DTO.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostWriteRequestDTO {

    private String title;
    private String body;
}