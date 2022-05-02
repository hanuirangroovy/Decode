package com.ssafy.authsvr.repository;

import com.ssafy.authsvr.entity.GenrePreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenrePreferenceRepository extends JpaRepository<GenrePreference,Integer> {
}
