package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.repository.PostRepository;
import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import com.example.fashioncommuni.recommend.service.UserCategoryScoresServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserCategoryScoresServiceImplTest {

    @Mock
    private UserCategoryScoresRepository userCategoryScoresRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private UserCategoryScoresServiceImpl userCategoryScoresService;

    @Test
    @DisplayName("사용자 카테고리 점수를 계산하는 테스트")
    public void testCalculateUserCategoryScores() {
        // 목 설정
        Long userId = 1L;
        UserCategoryScores userCategoryScores = mock(UserCategoryScores.class);
        when(userCategoryScoresRepository.findByUserId(userId)).thenReturn(Optional.of(userCategoryScores));

        // 테스트할 메서드 호출
        userCategoryScoresService.calculateUserCategoryScores(userId);

        // userCategoryScoresRepository의 메서드가 호출되었는지 검증
        verify(userCategoryScoresRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("게시물을 조회했을 때 카테고리 스코어를 계산하는 테스트")
    public void testViewPost() {
        // 목 설정
        Long userId = 1L;
        Long postId = 1L;
        Post post = new Post();
        post.setCategoryId(1L);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        UserCategoryScores userCategoryScores = mock(UserCategoryScores.class);
        when(userCategoryScoresRepository.findByUserId(userId)).thenReturn(Optional.of(userCategoryScores));

        // 테스트할 메서드 호출
        CategoryScores categoryScores = userCategoryScoresService.viewPost(userId, postId);

        // postRepository의 메서드가 호출되었는지 검증
        verify(postRepository, times(1)).findById(postId);
        // userCategoryScoresRepository의 메서드가 호출되었는지 검증
        verify(userCategoryScoresRepository, times(1)).findByUserId(userId);
        // 사용자 카테고리 점수를 저장하는 메서드가 호출되었는지 검증
        verify(userCategoryScoresRepository, times(1)).save(userCategoryScores);

        // categoryScores가 null이 아닌지 검증
        assertThat(categoryScores).isNotNull();
        // categoryScores의 카테고리가 post의 카테고리와 같은지 검증
        assertThat(categoryScores.getCategory()).isEqualTo(post.getCategoryId().toString());
        // categoryScores의 점수가 1.0인지 검증
        assertThat(categoryScores.getScores()).containsExactly(1.0);
    }

    // 더 많은 테스트 케이스나 예외 상황에 대한 테스트를 추가할 수 있습니다.
}
