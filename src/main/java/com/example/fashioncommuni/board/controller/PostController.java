package com.example.fashioncommuni.board.controller;

import com.example.fashioncommuni.board.DTO.comment.CommentResponseDTO;
import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.post.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.post.PostWriteRequestDTO;
import com.example.fashioncommuni.board.service.CommentService;
import com.example.fashioncommuni.board.service.PostService;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import com.example.fashioncommuni.member.service.SecurityUserService;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.service.UserCategoryScoresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;

@Controller // ToDo: RestController로 변경
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final CommentService commentService;
    private final SecurityUserService securityUserService;
    private final UserCategoryScoresService userCategoryScoresService;

    /**
     * 메인 페이지
     * @param authentication 유저 정보
     * @param model
     * @param pageable 페이징 처리
     * @param keyword 검색어
     * @param category_id 카테고리 ID
     * @return 메인 페이지
     */
    @GetMapping("/home")
    public String home(Authentication authentication , Model model, Pageable pageable, String keyword, Long category_id) { // @PageableDefault(page = 0, size = 10, sort = "postId", direction = Sort.Direction.DESC)

        SecurityUserDetailsDto userDetailsDto = (SecurityUserDetailsDto) authentication.getPrincipal();
        String authLoginId = userDetailsDto.getUsername();

        User user = securityUserService.findByLoginId(authLoginId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        try {
            UserCategoryScores userCategoryScores = userCategoryScoresService.findUserCategoryScoresByUserId(user.getId());
        } catch (Exception e) {
            return "init-interest";
        }

        if(keyword == null) {
            model.addAttribute("postList", postService.postList(pageable));
        } else if (category_id != null) {
            model.addAttribute("postList", postService.searchingPostCategory(category_id, pageable));
        } else {
            model.addAttribute("postList", postService.searchingPostList(keyword, pageable));
        }

        return "home";
    }


    /**
     * 게시글 작성
     * @return 게시글 작성 페이지
     */
    @GetMapping("/write")
    public String writeForm() {
        return "write";
    }

    /**
     * 게시글 작성 post
     * @param postWriteRequestDTO 게시글 정보
     * @param authentication 유저 정보
     * @return 게시글 디테일 페이지
     */
    @PostMapping("/write")
    public String write(PostWriteRequestDTO postWriteRequestDTO,
                        @ModelAttribute PostImageUploadDTO postImageUploadDTO,
                        Authentication authentication) {

        logger.info("postImageDTO is {}", postImageUploadDTO);
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        postService.savePost(postWriteRequestDTO, postImageUploadDTO, userDetails.getEmail());

        return "redirect:/post/home";
    }

    /**
     * 게시글 상세 조회
     * @param postId 게시글 ID
     * @param model
     * @return 게시글 상세 페이지
     */
    @GetMapping("/{postId}")
    public String postDetail(@PathVariable Long postId, Model model) {
        PostResponseDTO result = postService.postDetail(postId);
        List<CommentResponseDTO> commentResponseDTO = commentService.commentList(postId);

        model.addAttribute("comments", commentResponseDTO);
        model.addAttribute("dto", result);
        model.addAttribute("postId", postId);

        return "detail";
    }

    /**
     * 게시글 수정
     * @param postId 게시글 ID
     * @param model
     * @param authentication 유저 정보
     * @return 게시글 수정 페이지
     */
    @GetMapping("/{postId}/update")
    public String postUpdateForm(@PathVariable Long postId, Model model, Authentication authentication) {
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        PostResponseDTO result = postService.postDetail(postId);
        if (!result.getEmail().equals(userDetails.getEmail())) {
            return "redirect:/post/home";
        }

        model.addAttribute("dto", result);
        model.addAttribute("postId", postId);

        return "update";
    }

    /**
     * 게시글 수정 post
     * @param postId 게시글 ID
     * @param postWriteRequestDTO 수정 정보
     * @return 게시글 상세 조회 페이지
     */
    @PostMapping("/{postId}/update")
    public String postUpdate(@PathVariable Long postId, PostWriteRequestDTO postWriteRequestDTO) {
        postService.postUpdate(postId, postWriteRequestDTO);

        return "redirect:/post/" + postId;
    }

    /**
     * 게시글 삭제
     * @param postId 게시글 ID
     * @param authentication 유저 정보
     * @return 메인 페이지
     */
    @GetMapping("/{postId}/remove")
    public String postRemove(@PathVariable Long postId, Authentication authentication) {
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        PostResponseDTO result = postService.postDetail(postId);
        if (!Objects.equals(result.getEmail() , userDetails.getEmail())) {
            return "redirect:/post/home";
        }

        postService.postRemove(postId);

        return "redirect:/post/home";
    }
}