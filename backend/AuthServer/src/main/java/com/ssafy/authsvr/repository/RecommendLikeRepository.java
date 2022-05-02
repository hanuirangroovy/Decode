package com.ssafy.authsvr.repository;

import com.ssafy.authsvr.entity.RecommendLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecommendLikeRepository extends JpaRepository<RecommendLike,Integer> {

    Optional<RecommendLike> findByUserId(Integer userId);
}
