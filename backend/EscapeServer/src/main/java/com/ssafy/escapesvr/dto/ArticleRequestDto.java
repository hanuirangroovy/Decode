package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "유저게시판의 글 생성과 수정을 처리할 요청 DTO 클래스")
public class ArticleRequestDto {

    @ApiModelProperty(value = "유저게시판 글 제목", required = true)
    @NotBlank
    private String title; //제목

    @ApiModelProperty(value = "유저게시판 글 내용", required = true)
    @NotBlank
    private String content; //내용

    @ApiModelProperty(value = "유저게시판 작성자 id(번호)", required = true)
    @NotNull
    private Integer userId; //작성자

    @ApiModelProperty(value = "유저게시판 지역 대분류", required = true)
    @NotBlank
    private String largeRegion; //지역 대분류

    @ApiModelProperty(value = "유저게시판 지역 소분류", required = true)
    @NotBlank
    private String smallRegion; //지역 소분류


//    @Builder
//    public ArticleCreateRequestDto(String title, String content, Integer userId, String smallRegion){
//        this.title= title;
//        this.content = content;
//        this.userId = userId;
//        this.smallRegion = smallRegion;
//    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .largeRegion(largeRegion)
                .smallRegion(smallRegion)
                .build();
    }



}
