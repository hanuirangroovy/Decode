package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ApiModel(description = "유저게시판 응답 DTO 클래스")
public class ArticleResponseDto {

    @ApiModelProperty(value = "유저게시판 작성 글 번호")
    private Long id; // PK

    @ApiModelProperty(value = "유저게시판 글 제목", required = true)
    private String title; // 제목

    @ApiModelProperty(value = "유저게시판 글 내용", required = true)
    private String content; // 내용

    @ApiModelProperty(value = "유저게시판 지역 대분류", required = true)
    private String largeRegion; //지역 대분류

    @ApiModelProperty(value = "유저게시판 지역 소분류", required = true)
    private String smallRegion; //지역 소분류

    @ApiModelProperty(value = "유저게시판 작성자 id(번호)", required = true)
    private Integer userId; // 작성자

    @ApiModelProperty(value = "유저게시판 글 추천개수")
    private int recommend; // 추천개수

    @ApiModelProperty(value = "유저게시판 글 신고수")
    private int report; // 신고수

    @ApiModelProperty(value = "유저게시판 글 작성시간")
    private LocalDateTime createdAt; //작성시간

    @ApiModelProperty(value = "유저게시판 글 수정시간")
    private LocalDateTime modifiedAt; //수정시간

    @ApiModelProperty(value = "유저게시판 글 작성자 닉네임")
    private String nickName;//유저 닉네임

    @ApiModelProperty(value = "유저게시판 글 작성자 프로필사진")
    private String userImage; //유저 프로필 사진

    public ArticleResponseDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.largeRegion = entity.getLargeRegion();
        this.smallRegion = entity.getSmallRegion();
        this.recommend = entity.getRecommend();
        this.report = entity.getReport();
        this.userId = entity.getUserId();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.nickName= entity.getNickName();
        this.userImage = entity.getUserImage();
    }

}
