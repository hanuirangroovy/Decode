package com.ssafy.authsvr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "recommend_gender_age")
@Getter
@NoArgsConstructor
public class RecommendGenderAge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommend_gender_age_id")
    private Integer id;

    private Integer genderAgeOne;

    private Integer genderAgeTwo;

    private Integer genderAgeThree;

    private Integer genderAgeFour;

    private Integer genderAgeFive;

    private Integer genderAgeSix;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
