package com.ssafy.escapesvr.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Qna {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id; //문의글 번호

    @NotNull
    private String title; //제목

    @NotNull
    private String content; //내용

    @NotNull
    private Boolean isSecret; //보안글여부

    @NotNull
    private Boolean isNotice; //공지글여부

    @NotNull
    private LocalDateTime createdAt; //작성시간

    private LocalDateTime modifiedAt; //수정시간

    @NotNull
    private Integer userId; //유저 번호

    private String nickName;//유저 닉네임

    private String userImage; //유저 프로필 사진

    
    //qna 댓글
    @OneToMany(mappedBy = "qna", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<QnaComment> comments = new ArrayList<>();


}
