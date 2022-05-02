package com.ssafy.authsvr.payload.response;

import com.ssafy.authsvr.entity.GenrePreference;
import com.ssafy.authsvr.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailProfileResponse {
    private Integer id;

    private String nickName;

    private Integer age;

    private String gender;

    private String largeRegion;

    private String smallRegion;

    private Integer thrill;

    private Integer romance;

    private Integer reasoning;

    private Integer sfFantasy;

    private Integer adventure;

    private Integer comedy;

    private Integer crime;

    private Integer horror;

    private Integer adult;

    private Integer drama;

    public static UserDetailProfileResponse allProfileResponse(User user, GenrePreference genrePreference){
        return UserDetailProfileResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .age(user.getAge())
                .gender(user.getGender())
                .largeRegion(user.getLargeRegion())
                .smallRegion(user.getSmallRegion())
                .thrill(genrePreference.getThrill())
                .reasoning(genrePreference.getReasoning())
                .romance(genrePreference.getRomance())
                .sfFantasy(genrePreference.getSfFantasy())
                .adventure(genrePreference.getAdventure())
                .comedy(genrePreference.getComedy())
                .horror(genrePreference.getHorror())
                .adult(genrePreference.getAdult())
                .drama(genrePreference.getDrama())
                .build();
    }

    @Builder
    public UserDetailProfileResponse(Integer id, String nickName, Integer age, String gender, String largeRegion, String smallRegion, Integer thrill, Integer romance, Integer reasoning, Integer sfFantasy, Integer adventure, Integer comedy, Integer crime, Integer horror, Integer adult, Integer drama) {
        this.id = id;
        this.nickName = nickName;
        this.age = age;
        this.gender = gender;
        this.largeRegion = largeRegion;
        this.smallRegion = smallRegion;
        this.thrill = thrill;
        this.romance = romance;
        this.reasoning = reasoning;
        this.sfFantasy = sfFantasy;
        this.adventure = adventure;
        this.comedy = comedy;
        this.crime = crime;
        this.horror = horror;
        this.adult = adult;
        this.drama = drama;
    }
}
