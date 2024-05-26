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
import com.example.fashioncommuni.recommend.service.FindSimilarPeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
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
    private final FindSimilarPeopleService findSimilarPeopleService;

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
                .categoryId(postWriteRequestDTO.getCategoryId())
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

        return result.getPostId();
    }

    @Override
    public PostResponseDTO postDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
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
    public Page<PostResponseDTO> recommendPostList(Long userId ,Pageable pageable) {
        //FindSimilarPeopleService에서 유사한 유저들을 찾아서 그 유저들이 본 게시글을 추천하는 메소드

        List<Long> recommendPostIds = findSimilarPeopleService.getRecommendPostIds(userId);
        Page<Post> posts = postRepository.findByPostIdIn(recommendPostIds, pageable);
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
    public Long postUpdate(Long postId, PostWriteRequestDTO postWriteRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        post.update(postWriteRequestDTO.getTitle(), postWriteRequestDTO.getContent());
        postRepository.save(post);

        return post.getPostId();
    }

    @Override
    public void postRemove(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        postRepository.delete(post);
    }

    @Override
    public Page<PostResponseDTO> searchingPostCategory(Long category_id, Pageable pageable) {
        Page<Post> posts = postRepository.findByCategoryId(category_id, pageable);
        return getPostResponseDTOS(pageable, posts);
    }
}