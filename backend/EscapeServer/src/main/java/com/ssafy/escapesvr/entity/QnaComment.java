package com.ssafy.escapesvr.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qna_comment_id")
    private Long id; //공지사항 댓글 ID

    @NotNull
    private String content; //댓글 내용

    private LocalDateTime createdAt;//작성시간

    private LocalDateTime modifiedAt;//수정시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna; //qna 글 번호

    @NotNull
    private Integer userId; //사용자 id(번호)

    private String nickName;//유저 닉네임

    private String userImage; //유저 프로필 사진


}
