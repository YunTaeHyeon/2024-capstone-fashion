package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.post.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.post.PostWriteRequestDTO;
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
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    @Value("${file.postImagePath}")
    private String uploadFolder;

    @Override
    @Transactional
    public Long savePost(PostWriteRequestDTO postWriteRequestDTO,
                         PostImageUploadDTO postImageUploadDTO,
                         String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Post result = Post.builder()
                .title(postWriteRequestDTO.getTitle())
                .content(postWriteRequestDTO.getContent())
                .category_id(postWriteRequestDTO.getCategory_id())
                .user(user)
                .build();

        postRepository.save(result);

        if (postImageUploadDTO.getFiles() != null && !postImageUploadDTO.getFiles().isEmpty()) {
            for (MultipartFile file : postImageUploadDTO.getFiles()) {
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File(uploadFolder + imageFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                PostImage image = PostImage.builder()
                        .url("/postImages/" + imageFileName)
                        .post(result)
                        .build();

                postImageRepository.save(image);
            }
        }

        return result.getPost_id();
    }

    @Override
    public PostResponseDTO postDetail(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        PostResponseDTO result = PostResponseDTO.builder()
                .post(post)
                .build();

        return result;
    }

    @Override
    public Page<PostResponseDTO> postList(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return getPostResponseDTOS(pageable, posts);
    }

    @Override
    public Page<PostResponseDTO> searchingPostList(String keyword, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitle(keyword, pageable);
        return getPostResponseDTOS(pageable, posts);
    }

    private Page<PostResponseDTO> getPostResponseDTOS(Pageable pageable, Page<Post> posts) {
        List<PostResponseDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDTO result = PostResponseDTO.builder()
                    .post(post)
                    .build();
            postDTOs.add(result);
        }

        return new PageImpl<>(postDTOs, pageable, posts.getTotalElements());
    }

    @Override
    public Long postUpdate(Long post_id, PostWriteRequestDTO postWriteRequestDTO) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        post.update(postWriteRequestDTO.getTitle(), postWriteRequestDTO.getContent());
        postRepository.save(post);

        return post.getPost_id();
    }

    @Override
    public void postRemove(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postRepository.delete(post);
    }

    @Override
    public Page<PostResponseDTO> searchingPostCategory(Long category_id, Pageable pageable) {
        Page<Post> posts = postRepository.findByCategory_id(category_id, pageable);
        return getPostResponseDTOS(pageable, posts);
    }
}