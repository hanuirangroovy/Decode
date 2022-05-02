package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findByUserId(Integer userId, Pageable pageable);

    Page<Qna> findAll(Pageable pageable);
    //Page<Qna> findAllOrderByCreatedAt(Pageable pageable);
}
