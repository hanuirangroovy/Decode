package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.Qna;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "1:1 문의 관련 응답 dto")
public class QnaResponseDto {

    @ApiModelProperty(value = "문의글 번호", required = true)
    private Long id; //문의글 번호

    @ApiModelProperty(value = "문의글 제목", required = true)
    private String title; //제목

    @ApiModelProperty(value = "문의글 내용", required = true)
    private String content; //내용

    @ApiModelProperty(value = "보안글 여부")
    private Boolean isSecret; //보안글여부

    @ApiModelProperty(value = "공지글 여부")
    private Boolean isNotice; //공지글여부

    @ApiModelProperty(value = "1:1 문의 작성시간")
    private LocalDateTime createdAt; //작성시간

    @ApiModelProperty(value = "1:1 문의 수정시간")
    private LocalDateTime modifiedAt; //수정시간

    @ApiModelProperty(value = "사용자 id(번호)", required = true)
    private Integer userId; //사용자 번호

    @ApiModelProperty(value = "qna 게시판 글 작성자 닉네임")
    private String nickName;//유저 닉네임

    @ApiModelProperty(value = "qna 게시판 글 작성자 프로필사진")
    private String userImage; //유저 프로필 사진

    public QnaResponseDto(Qna qna){
        this.id= qna.getId();
        this.title= qna.getTitle();;
        this.content= qna.getContent();
        this.createdAt =qna.getCreatedAt();
        this.modifiedAt= qna.getModifiedAt();
        this.isSecret=qna.getIsSecret();
        this.isNotice=qna.getIsNotice();
        this.userId=qna.getUserId();
        this.nickName =qna.getNickName();
        this.userImage = qna.getUserImage();
    }



}
