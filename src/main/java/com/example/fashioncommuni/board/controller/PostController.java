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
     * 홈 화면
     * @return 홈 화면
     */
    @GetMapping("/home")
    public String home(Authentication authentication , Model model, Pageable pageable, String keyword) { // @PageableDefault(page = 0, size = 10, sort = "post_id", direction = Sort.Direction.DESC)

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

        return "redirect:/";
    }

    /**
     * 게시글 상세 조회
     * @param post_id 게시글 ID
     * @param model
     * @return 게시글 상세 페이지
     */
    @GetMapping("/{post_id}")
    public String postDetail(@PathVariable Long post_id, Model model) {
        PostResponseDTO result = postService.postDetail(post_id);
        List<CommentResponseDTO> commentResponseDTO = commentService.commentList(post_id);

        model.addAttribute("comments", commentResponseDTO);
        model.addAttribute("dto", result);
        model.addAttribute("post_id", post_id);

        return "detail";
    }

    /**
     * 게시글 수정
     * @param post_id 게시글 ID
     * @param model
     * @param authentication 유저 정보
     * @return 게시글 수정 페이지
     */
    @GetMapping("/{post_id}/update")
    public String postUpdateForm(@PathVariable Long post_id, Model model, Authentication authentication) {
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        PostResponseDTO result = postService.postDetail(post_id);
        if (!result.getEmail().equals(userDetails.getEmail())) {
            return "detail";
        }

        model.addAttribute("dto", result);
        model.addAttribute("post_id", post_id);

        return "update";
    }

    /**
     * 게시글 수정 post
     * @param post_id 게시글 ID
     * @param postWriteRequestDTO 수정 정보
     * @return 게시글 상세 조회 페이지
     */
    @PostMapping("/{post_id}/update")
    public String postUpdate(@PathVariable Long post_id, PostWriteRequestDTO postWriteRequestDTO) {
        postService.postUpdate(post_id, postWriteRequestDTO);

        return "redirect:/post/" + post_id;
    }

    /**
     * 게시글 삭제
     * @param post_id 게시글 ID
     * @param authentication 유저 정보
     * @return 메인 페이지
     */
    @GetMapping("/{post_id}/remove")
    public String postRemove(@PathVariable Long post_id, Authentication authentication) {
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        PostResponseDTO result = postService.postDetail(post_id);
        if (!Objects.equals(result.getEmail() , userDetails.getEmail())) {
            return "redirect:/";
        }

        postService.postRemove(post_id);

        return "redirect:/";
    }
}