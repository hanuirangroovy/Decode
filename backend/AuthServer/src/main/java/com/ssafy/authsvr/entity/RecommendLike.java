package com.ssafy.authsvr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "recommend_like")
@Getter
@NoArgsConstructor
public class RecommendLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommend_like_id")
    private Integer id;

    private Integer likeOne;

    private Integer likeTwo;

    private Integer likeThree;

    private Integer likeFour;

    private Integer likeFive;

    private Integer likeSix;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
