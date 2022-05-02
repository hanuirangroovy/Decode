package com.ssafy.escapesvr.dto;


import com.ssafy.escapesvr.entity.Notice;
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
@ApiModel(description = "공지게시판 관련 요청 dto")
public class NoticeRequestDto {

    @ApiModelProperty(value = "공지글 번호", required = true)
    @NotNull
    private Long id; //문의글 번호

    @ApiModelProperty(value = "공지글 제목", required = true)
    @NotBlank
    private String title; //제목

    @ApiModelProperty(value = "공지사항 내용", required = true)
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

    public NoticeRequestDto(Notice notice){
        this.id= notice.getId();
        this.title= notice.getTitle();;
        this.content= notice.getContent();
        this.isSecret= notice.getIsSecret();
        this.isNotice= notice.getIsNotice();
        this.userId= notice.getUserId();
    }



}
