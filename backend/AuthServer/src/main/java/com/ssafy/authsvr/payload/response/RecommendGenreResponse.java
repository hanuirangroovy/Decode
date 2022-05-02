package com.ssafy.authsvr.payload.response;

import com.ssafy.authsvr.entity.RecommendGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendGenreResponse {

    private Integer genreOne;

    private Integer genreTwo;

    private Integer genreThree;

    private Integer genreFour;

    private Integer genreFive;

    private Integer genreSix;

    public static RecommendGenreResponse recommendGenreResponseBuilder(RecommendGenre recommendGenre){
        return RecommendGenreResponse.builder()
                .genreOne(recommendGenre.getGenreOne())
                .genreTwo(recommendGenre.getGenreTwo())
                .genreThree(recommendGenre.getGenreThree())
                .genreFour(recommendGenre.getGenreFour())
                .genreFive(recommendGenre.getGenreFive())
                .genreSix(recommendGenre.getGenreSix())
                .build();

    }

    @Builder
    public RecommendGenreResponse(Integer genreOne, Integer genreTwo, Integer genreThree, Integer genreFour, Integer genreFive, Integer genreSix) {
        this.genreOne = genreOne;
        this.genreTwo = genreTwo;
        this.genreThree = genreThree;
        this.genreFour = genreFour;
        this.genreFive = genreFive;
        this.genreSix = genreSix;
    }
}
