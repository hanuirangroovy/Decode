package com.ssafy.authsvr.repository;

import com.ssafy.authsvr.entity.RecommendGenderAge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecommendGenderAgeRepository extends JpaRepository<RecommendGenderAge,Integer> {

    Optional<RecommendGenderAge> findByUserId(Integer userId);
}
