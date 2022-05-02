package com.ssafy.escapesvr.repository;


import com.ssafy.escapesvr.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByLargeRegion(String largeRegion);

}
