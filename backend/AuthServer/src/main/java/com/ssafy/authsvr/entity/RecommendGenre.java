package com.ssafy.authsvr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "recommend_genre")
@Getter
@NoArgsConstructor
public class RecommendGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommend_genre_id")
    private Integer id;

    private Integer genreOne;

    private Integer genreTwo;

    private Integer genreThree;

    private Integer genreFour;

    private Integer genreFive;

    private Integer genreSix;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
