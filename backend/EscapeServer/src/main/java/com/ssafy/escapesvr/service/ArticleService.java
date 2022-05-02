package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.ArticleRequestDto;
import com.ssafy.escapesvr.dto.ArticleResponseDto;
import com.ssafy.escapesvr.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    /* 조회 */
    Page<ArticleResponseDto> getAllArticle(Pageable pageable);// 모든 게시글 조회
    ArticleResponseDto getArticle(Long id);// 게시물 id를 통해 하나의 게시글 조회
    Page<ArticleResponseDto> getMyArticleList(Integer userId, Pageable pageable); // 회원 별 게시글 조회

    /* 저장, 수정, 삭제 */
    ArticleResponseDto save(ArticleRequestDto articleRequestDto); //게시글 저장
    ArticleResponseDto updateArticle(ArticleRequestDto articleRequestDto, Long id); //게시글 수정
    void deleteArticle(Long id); //게시글 아이디로 게시글 삭제

    /* 검색 */
//    List<ArticleResponseDto> postList(String smallRegion, SearchDto searchDto); //게시글 검색
    Page<ArticleResponseDto> postList(String largeRegion , String smallRegion , Pageable pageable); //게시글 검색

    /* 추천, 신고 기능 */
    Integer recommendArticle(Long id); // 게시글 아이디로 추천 수 증가
    Integer reportArticle(Long id); // 게시글 아이디로 신고 수 증가
    Integer cancelRecommendArticle(Long id); // 추천 취소
    Integer cancleReportArticle(Long id); // 신고 취소

}
