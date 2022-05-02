package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findByUserId(Integer userId, Pageable pageable);
    //Page<Notice> findAllOrderByCreatedAt(Pageable pageable);
    Page<Notice> findAll(Pageable pageable);
}
