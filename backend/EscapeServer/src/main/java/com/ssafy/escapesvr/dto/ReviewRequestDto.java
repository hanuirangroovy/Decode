package com.ssafy.escapesvr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    @ApiModelProperty(value = "유저 아이디",example = "1")
    private Integer userId;
    @ApiModelProperty(value = "내가 리뷰 작성하는 테마 아이디",example = "1")
    private Integer themeId;
    @ApiModelProperty(value = "내 점수",example = "8")
    private Integer myScore;
    @ApiModelProperty(value = "클리어 시간",example = "45")
    private Integer clearTime;
    @ApiModelProperty(value = "리뷰 내용",example = "재밌었어요")
    private String reviewContent;

}
