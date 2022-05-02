package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.QnaComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "qna/공지 댓글 관련 응답 dto")
public class QnaCommentResponseDto {

    @ApiModelProperty(value = "공지사항/문의글 댓글 번호", required = true)
    private Long id; //댓글 번호

    @ApiModelProperty(value = "공지사항/문의글 댓글 내용", required = true)
    private String content; //댓글 내용

    @ApiModelProperty(value = "공지사항/문의글 댓글 작성자 id(번호)", required = true)
    private Integer userId; //댓글 작성자 id

    @ApiModelProperty(value = "공지사항/문의글 댓글 작성시간")
    private LocalDateTime createdAt;//작성시간

    @ApiModelProperty(value = "공지사항/문의글 댓글 수정시간")
    private LocalDateTime modifiedAt;//수정시간

    @ApiModelProperty(value = "공지사항/문의글 번호", required = true)
    private Long QnaId; //문의글 번호

    @ApiModelProperty(value = "공지게시판 댓글 작성자 닉네임")
    private String nickName;//유저 닉네임

    @ApiModelProperty(value = "공지게시판 댓글 작성자 프로필사진")
    private String userImage; //유저 프로필 사진


    public QnaCommentResponseDto(QnaComment qnaComment){
        this.id= qnaComment.getId();
        this.content= qnaComment.getContent();
        this.userId = qnaComment.getUserId();
        this.createdAt = qnaComment.getCreatedAt();
        this.modifiedAt = qnaComment.getModifiedAt();
        this.QnaId = qnaComment.getQna().getId();
        this.nickName = qnaComment.getNickName();
        this.userImage = qnaComment.getUserImage();
    }


}
