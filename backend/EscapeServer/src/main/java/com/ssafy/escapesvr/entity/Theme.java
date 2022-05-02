package com.ssafy.escapesvr.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Integer id;

    //테마이름
    @NotNull
    @Column(name="theme_name")
    private String name;

    //장르
    @NotNull
    private String genre;

    //공포여부
    @NotNull
    private Integer isScared;

    //난이도
    @NotNull
    private Integer level;

    //최대인원수
    @NotNull
    private Integer maxNumber;

    //시간
    @NotNull
    private Integer time;

    //평점
    @NotNull
    private Double score;

    //리뷰수
    @NotNull
    private Integer reviewCnt;

    //유형
    @NotNull
    private String type;

    //포스터URL
    @NotNull
    @Column(length = 1000)
    private String posterUrl;

    //예약 페에지URL
    @NotNull
    @Column(length = 1000)
    private String reserveUrl;

    //1인 가능여부
    @NotNull
    private Integer isSingleplay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recommend_number_id")
    private RecommendNumber recommendNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "theme",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ThemeReview> themeReviews=new ArrayList<>();

}
