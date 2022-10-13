package com.okbank.blockchain.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach // Junit 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글저장_불러오기")
    public void postsSaveAndFindAll() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // id 값 유무에 따라 update/insert 처리된다
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("kim")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("BaseTimeEntity_등록")
    public void saveBaseTimeEntity() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 1, 24, 15, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .author("author")
                .content("content")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println("createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}