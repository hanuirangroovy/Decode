package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.Notice;
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
@ApiModel(description = "공지사항 관련 응답 dto")
public class NoticeResponseDto {

    @ApiModelProperty(value = "공지글 번호", required = true)
    private Long id; //문의글 번호

    @ApiModelProperty(value = "공지글 제목", required = true)
    private String title; //제목

    @ApiModelProperty(value = "공지글 내용", required = true)
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

    public NoticeResponseDto(Notice notice){
        this.id= notice.getId();
        this.title= notice.getTitle();;
        this.content= notice.getContent();
        this.createdAt =notice.getCreatedAt();
        this.modifiedAt= notice.getModifiedAt();
        this.isSecret=notice.getIsSecret();
        this.isNotice=notice.getIsNotice();
        this.userId=notice.getUserId();
        this.nickName =notice.getNickName();
        this.userImage = notice.getUserImage();
    }



}
