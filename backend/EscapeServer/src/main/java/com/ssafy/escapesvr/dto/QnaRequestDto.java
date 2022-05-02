package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.Qna;
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
@ApiModel(description = "1:1 문의 관련 요청 dto")
public class QnaRequestDto {

    @ApiModelProperty(value = "문의글 번호", required = true)
    @NotNull
    private Long id; //문의글 번호

    @ApiModelProperty(value = "문의글 제목", required = true)
    @NotBlank
    private String title; //제목

    @ApiModelProperty(value = "공지사항/문의글 내용", required = true)
    @NotBlank
    private String content; //내용

    @ApiModelProperty(value = "보안글 여부", required = true)
    @NotNull
    private Boolean isSecret; //보안글여부

    @ApiModelProperty(value = "공지글 여부", required = true)
    @NotNull
    private Boolean isNotice; //공지글여부

    @ApiModelProperty(value = "사용자 id(번호)", required = true)
    @NotNull
    private Integer userId; //사용자 번호

    public QnaRequestDto(Qna qna){
        this.id= qna.getId();
        this.title= qna.getTitle();;
        this.content= qna.getContent();
        this.isSecret=qna.getIsSecret();
        this.isNotice=qna.getIsNotice();
        this.userId=qna.getUserId();
    }



}
