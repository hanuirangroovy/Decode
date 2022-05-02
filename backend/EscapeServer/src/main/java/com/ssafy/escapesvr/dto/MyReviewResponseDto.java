package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.entity.ThemeReview;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewResponseDto {
    @ApiModelProperty(value = "리뷰 아이디", example = "1")
    private Integer themeReviewId;
    @ApiModelProperty(value = "작성시간", example = "1")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "리뷰내용", example = "1")
    private String reviewContent;
    @ApiModelProperty(value = "내 리뷰점수", example = "1")
    private Integer myScore;
    @ApiModelProperty(value = "테마 아이디", example = "1")
    private Integer themeId;
    @ApiModelProperty(value = "테마이름", example = "1")
    private String themeName;

    public MyReviewResponseDto(ThemeReview review, Theme theme){
        themeReviewId=review.getId();
        createdAt=review.getCreatedAt();
        reviewContent=review.getReviewContent();
        myScore=review.getMyScore();
        themeId=theme.getId();
        themeName=theme.getName();
    }
}
