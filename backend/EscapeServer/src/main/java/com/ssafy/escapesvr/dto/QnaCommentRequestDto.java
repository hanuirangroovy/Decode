package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.QnaComment;
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
@ApiModel(description = "qna/공지 댓글 관련 요청 dto")
public class QnaCommentRequestDto {


    @ApiModelProperty(value = "공지사항/문의글 댓글 내용", required = true)
    @NotBlank
    private String content; //댓글 내용

    @ApiModelProperty(value = "공지사항/문의글 번호", required = true)
    @NotNull
    private Long QnaId; //문의글 번호

    @ApiModelProperty(value = "댓글 작성자 id(번호)", required = true)
    @NotNull
    private Integer userId; //사용자 id

    public QnaCommentRequestDto(QnaComment qnaComment){
        this.content = qnaComment.getContent();
        this.QnaId = qnaComment.getQna().getId();
        this.userId = qnaComment.getUserId();
    }

//    public ArticleComment toEntity() {
//        return Article.builder()
//                .content(content)
//                .userId(userId)
//                .build();
//    }


}
