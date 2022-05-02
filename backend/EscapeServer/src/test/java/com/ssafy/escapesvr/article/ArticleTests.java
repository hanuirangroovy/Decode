//package com.ssafy.escapesvr.article;
//
//import com.ssafy.escapesvr.entity.Article;
//import com.ssafy.escapesvr.repository.ArticleRepository;
//import com.ssafy.escapesvr.service.UrlToMultipartService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class ArticleTests {
//
//    @Autowired
//    ArticleRepository articleRepository;
//
//    @Autowired
//    UrlToMultipartService urlToMultipartService;
//
////    @Test
////    void save() {
////
////        // 1. 게시글 파라미터 생성
////        Article params = Article.builder()
////                .title("3번 게시글 제목")
////                .content("3번 게시글 내용")
////                .userId(3)
////                .smallRegion("서울강남")
////                .build();
////
////        // 2. 게시글 저장
////        articleRepository.save(params);
////
////        // 3. 1번 게시글 정보 조회
////        Article entity = articleRepository.findById((long) 1).get();
////        assertThat(entity.getTitle()).isEqualTo("3번 게시글 제목");
////        assertThat(entity.getContent()).isEqualTo("3번 게시글 내용");
////        assertThat(entity.getUserId()).isEqualTo(3);
////    }
//    @Test
//    void test(){
//        urlToMultipartService.changeUrl();
//    }
//
//
//
//}
//
