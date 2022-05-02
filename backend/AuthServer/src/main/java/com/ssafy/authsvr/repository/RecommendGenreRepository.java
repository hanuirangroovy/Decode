package com.ssafy.authsvr.repository;

import com.ssafy.authsvr.entity.RecommendGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecommendGenreRepository extends JpaRepository<RecommendGenre,Integer> {
    Optional<RecommendGenre> findByUserId(Integer userId);

}
