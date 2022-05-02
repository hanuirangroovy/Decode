package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.ArticleComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "유저게시판 댓글 관련 응답 dto")
public class ArticleCommentResponseDto {


    @ApiModelProperty(value = "유저게시판 댓글 번호", required = true)
    private Long id; //댓글 번호

    @ApiModelProperty(value = "유저게시판 댓글 내용", required = true)
    private String content; //댓글 내용

    @ApiModelProperty(value = "유저게시판 댓글 작성자 id(번호)", required = true)
    private Integer userId; //댓글 작성자 id

    @ApiModelProperty(value = "유저게시판 댓글 작성 시간")
    private LocalDateTime createdAt;//작성시간

    @ApiModelProperty(value = "유저게시판 댓글 수정 시간")
    private LocalDateTime modifiedAt;//수정시간

    @ApiModelProperty(value = "유저게시판 작성글 번호(articleId)", required = true)
    private Long articleId; //게시글 번호

    @ApiModelProperty(value = "유저게시판 작성글 제목")
    private String articleTitle; //게시글 제목

    @ApiModelProperty(value = "유저게시판 댓글 작성자 닉네임")
    private String nickName;//유저 닉네임

    @ApiModelProperty(value = "유저게시판 댓글 작성자 프로필사진")
    private String userImage; //유저 프로필 사진




    public ArticleCommentResponseDto(ArticleComment articleComment){
        this.id=articleComment.getId();
        this.content= articleComment.getContent();
        this.userId = articleComment.getUserId();
        this.createdAt = articleComment.getCreatedAt();
        this.modifiedAt = articleComment.getModifiedAt();
        this.articleId = articleComment.getArticle().getId();
        this.articleTitle= articleComment.getArticle().getTitle();
        this.nickName = articleComment.getNickName();
        this.userImage = articleComment.getUserImage();
    }


}
