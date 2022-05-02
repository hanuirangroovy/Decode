package com.ssafy.escapesvr.repository;

import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.repository.querydsl.InformationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme,Integer>, InformationRepository {

    List<Theme> findTop6ByReviewCntGreaterThanEqualOrderByScoreDesc(Integer cnt);

}
