package com.ssafy.escapesvr.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id; //게시글 ID

    @NotNull
    private String title; //제목

    @NotNull
    private String content; //내용

    @NotNull
    private Integer userId; //사용자id

    @NotNull
    private String largeRegion; //지역대분류

    @NotNull
    private String smallRegion; //지역소분류

    private int recommend; //추천개수

    private int report; //신고횟수

    private LocalDateTime createdAt; //작성시간

    private LocalDateTime modifiedAt; //수정시간

    private String nickName;//유저 닉네임

    private String userImage; //유저 프로필 사진



    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleComment> comments = new ArrayList<>();


    //추천 개수 증가
    public void increaseRecommend() {
        this.recommend++;
    }

    //신고 횟수 증가
    public void increaseReport() {
        this.report++;
    }


    //빌더
    @Builder
    public Article(String title, String content, String largeRegion, String smallRegion, Integer userId) {
        this.title= title;
        this.content= content;
        this.largeRegion = largeRegion;
        this.smallRegion = smallRegion;
        this.userId = userId;
    }

    //게시글 수정
    public void update(String title, String content, String largeRegion, String smallRegion, Integer userId, LocalDateTime modifiedAt) {
        this.title = title;
        this.content = content;
        this.largeRegion = largeRegion;
        this.smallRegion = smallRegion;
        this.userId = userId;
        this.modifiedAt = LocalDateTime.now();
    }


}
