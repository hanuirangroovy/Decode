package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.Article;
import com.ssafy.escapesvr.entity.ArticleComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "유저게시판 댓글 관련 요청 dto")
public class ArticleCommentRequestDto {


    @ApiModelProperty(value = "유저게시판 댓글 내용", required = true)
    @NotBlank
    private String content; //댓글 내용

    @ApiModelProperty(value = "유저게시판 작성글 번호(articleId)", required = true)
    @NotNull
    private Long articleId; //게시글 번호

    @ApiModelProperty(value = "유저게시판 댓글 작성자 번호(userId)", required = true)
    @NotNull
    private Integer userId; //사용자 id

    public ArticleCommentRequestDto(ArticleComment articleComment){
        this.content = articleComment.getContent();
        this.articleId = articleComment.getArticle().getId();
        this.userId = articleComment.getUserId();
    }

//    public ArticleComment toEntity() {
//        return Article.builder()
//                .content(content)
//                .userId(userId)
//                .build();
//    }


}
