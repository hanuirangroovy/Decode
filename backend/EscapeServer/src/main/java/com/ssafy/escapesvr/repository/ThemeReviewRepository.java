package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.entity.ThemeReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeReviewRepository extends JpaRepository<ThemeReview,Integer> {
    Page<ThemeReview> findByTheme(Theme theme, Pageable pageable);
    List<ThemeReview> findAllByUserId(Integer userId);
    List<ThemeReview> findAllByUserId(Integer userId,Pageable pageable);

}
