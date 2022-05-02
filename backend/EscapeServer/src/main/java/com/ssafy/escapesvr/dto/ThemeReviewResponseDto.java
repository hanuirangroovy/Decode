package com.ssafy.escapesvr.dto;

import com.ssafy.escapesvr.entity.ThemeReview;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeReviewResponseDto {

    @ApiModelProperty(value = "테마 아이디", example = "1", required = true)
    private Integer themeReviewId;

    private Integer userId;

    private String userNickName;

    private Integer myScore;

    private String reviewContent;

    private LocalDateTime createdAt;

    private Integer clearTime;

//    public  ThemeReviewResponseDto(ThemeReview themeReview){
//        themeReviewId=themeReview.getId();
//        userNickName=themeReview.getUserNickName();
//        myScore=themeReview.getMyScore();
//        reviewContent=themeReview.getReviewContent();
//        createdAt=themeReview.getCreatedAt();
//        clearTime=themeReview.getClearTime();
//    }

}
