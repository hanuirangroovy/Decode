package com.ssafy.authsvr.entity;

import com.ssafy.authsvr.payload.request.UserPreferenceModifyReqeust;
import com.ssafy.authsvr.payload.request.UserProfilePreferenceRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "genre_preference")
@Getter
@NoArgsConstructor
public class GenrePreference {

    @Id
    @Column(name = "genre_preference_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToOne(mappedBy = "genrePreference")
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    public GenrePreference(Integer thrill, Integer romance, Integer reasoning, Integer sfFantasy,
                            Integer adventure, Integer comedy, Integer crime, Integer horror, Integer adult, Integer drama, User user) {
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
        this.user = user;
    }

    public static GenrePreference genrePreferenceBuild(UserProfilePreferenceRequest genre, User user) {
        return GenrePreference.builder()
                .thrill(genre.getUserPreference().getThrill())
                .romance(genre.getUserPreference().getRomance())
                .reasoning(genre.getUserPreference().getReasoning())
                .sfFantasy(genre.getUserPreference().getSfFantasy())
                .adventure(genre.getUserPreference().getAdventure())
                .comedy(genre.getUserPreference().getComedy())
                .crime(genre.getUserPreference().getCrime())
                .horror(genre.getUserPreference().getHorror())
                .adult(genre.getUserPreference().getAdult())
                .drama(genre.getUserPreference().getDrama())
                .user(user)
                .build();
    }

    public void setGenrePreferenceInfoModified(UserPreferenceModifyReqeust preferenceModifyRequest, User user){
        this.thrill = preferenceModifyRequest.getThrill();
        this.romance = preferenceModifyRequest.getRomance();
        this.reasoning = preferenceModifyRequest.getReasoning();
        this.sfFantasy = preferenceModifyRequest.getSfFantasy();
        this.adventure = preferenceModifyRequest.getAdventure();
        this.comedy = preferenceModifyRequest.getComedy();
        this.crime = preferenceModifyRequest.getCrime();
        this.horror = preferenceModifyRequest.getHorror();
        this.adult = preferenceModifyRequest.getAdult();
        this.drama = preferenceModifyRequest.getDrama();
        this.user = user;
    }
}