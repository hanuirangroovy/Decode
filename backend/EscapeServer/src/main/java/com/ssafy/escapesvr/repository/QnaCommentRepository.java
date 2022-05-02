package com.ssafy.escapesvr.repository;


import com.ssafy.escapesvr.entity.Qna;
import com.ssafy.escapesvr.entity.QnaComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {

    List<QnaComment> findByQna(Qna Qna);
    Page<QnaComment> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
}
