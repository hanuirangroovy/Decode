package com.ssafy.authsvr.payload.response;

import com.ssafy.authsvr.entity.RecommendLike;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendLikeResponse {

    private Integer likeOne;

    private Integer likeTwo;

    private Integer likeThree;

    private Integer likeFour;

    private Integer likeFive;

    private Integer likeSix;

    public static RecommendLikeResponse recommendLikeResponseBuilder(RecommendLike recommendLike){
        return RecommendLikeResponse.builder()
                .likeOne(recommendLike.getLikeOne())
                .likeTwo(recommendLike.getLikeTwo())
                .likeThree(recommendLike.getLikeThree())
                .likeFour(recommendLike.getLikeFour())
                .likeFive(recommendLike.getLikeFive())
                .likeSix(recommendLike.getLikeSix())
                .build();

    }

    @Builder
    public RecommendLikeResponse(Integer likeOne, Integer likeTwo, Integer likeThree,
                                 Integer likeFour, Integer likeFive, Integer likeSix) {
        this.likeOne = likeOne;
        this.likeTwo = likeTwo;
        this.likeThree = likeThree;
        this.likeFour = likeFour;
        this.likeFive = likeFive;
        this.likeSix = likeSix;
    }
}
