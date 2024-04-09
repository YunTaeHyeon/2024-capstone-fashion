package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.posts.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.posts.PostWriteRequestDTO;
import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.domain.PostImage;
import com.example.fashioncommuni.board.repository.PostImageRepository;
import com.example.fashioncommuni.board.repository.PostRepository;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postsRepository;
    private final PostImageRepository postsImageRepository;

    @Value("${file.postsImagePath}")
    private String uploadFolder;

    @Override
    @Transactional
    public Long savePost(PostWriteRequestDTO postsWriteRequestDTO,
                         PostImageUploadDTO postsImageUploadDTO,
                         String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        Post result = Post.builder()
                .title(postsWriteRequestDTO.getTitle())
                .body(postsWriteRequestDTO.getBody())
                .user(user)
                .build();

        postsRepository.save(result);

        if (postsImageUploadDTO.getFiles() != null && !postsImageUploadDTO.getFiles().isEmpty()) {
            for (MultipartFile file : postsImageUploadDTO.getFiles()) {
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File(uploadFolder + imageFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                PostImage image = PostImage.builder()
                        .url("/postsImages/" + imageFileName)
                        .post(result)
                        .build();

                postsImageRepository.save(image);
            }
        }

        return result.getPost_id();
    }

    @Override
    public PostResponseDTO postsDetail(Long post_id) {
        Post posts = postsRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        PostResponseDTO result = PostResponseDTO.builder()
                .post(posts)
                .build();

        return result;
    }

    @Override
    public Page<PostResponseDTO> postsList(Pageable pageable) {
        Page<Post> posts = postsRepository.findAll(pageable);
        return getPostResponseDTOS(pageable, posts);
    }

    @Override
    public Page<PostResponseDTO> searchingPostList(String keyword, Pageable pageable) {
        Page<Post> posts = postsRepository.findByTitle(keyword, pageable);
        return getPostResponseDTOS(pageable, posts);
    }

    private Page<PostResponseDTO> getPostResponseDTOS(Pageable pageable, Page<Post> posts) {
        List<PostResponseDTO> postsDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDTO result = PostResponseDTO.builder()
                    .post(post)
                    .build();
            postsDTOs.add(result);
        }

        return new PageImpl<>(postsDTOs, pageable, posts.getTotalElements());
    }

    @Override
    public Long postsUpdate(Long post_id, PostWriteRequestDTO postsWriteRequestDTO) {
        Post posts = postsRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        posts.update(postsWriteRequestDTO.getTitle(), postsWriteRequestDTO.getBody());
        postsRepository.save(posts);

        return posts.getPost_id();
    }

    @Override
    public void postsRemove(Long post_id) {
        Post posts = postsRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postsRepository.delete(posts);
    }
}