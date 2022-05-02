package com.ssafy.authsvr.payload.response;

import com.ssafy.authsvr.entity.RecommendGenderAge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendGenderAgeResponse {

    private Integer genderAgeOne;

    private Integer genderAgeTwo;

    private Integer genderAgeThree;

    private Integer genderAgeFour;

    private Integer genderAgeFive;

    private Integer genderAgeSix;

    public static RecommendGenderAgeResponse recommendGenderAgeResponseBuilder(RecommendGenderAge recommendGenderAge){
        return RecommendGenderAgeResponse.builder()
                .genderAgeOne(recommendGenderAge.getGenderAgeOne())
                .genderAgeTwo(recommendGenderAge.getGenderAgeTwo())
                .genderAgeThree(recommendGenderAge.getGenderAgeThree())
                .genderAgeFour(recommendGenderAge.getGenderAgeFour())
                .genderAgeFive(recommendGenderAge.getGenderAgeFive())
                .genderAgeSix(recommendGenderAge.getGenderAgeSix())
                .build();
    }

    @Builder
    public RecommendGenderAgeResponse(Integer id, Integer genderAgeOne, Integer genderAgeTwo, Integer genderAgeThree, Integer genderAgeFour, Integer genderAgeFive, Integer genderAgeSix) {
        this.genderAgeOne = genderAgeOne;
        this.genderAgeTwo = genderAgeTwo;
        this.genderAgeThree = genderAgeThree;
        this.genderAgeFour = genderAgeFour;
        this.genderAgeFive = genderAgeFive;
        this.genderAgeSix = genderAgeSix;
    }
}
