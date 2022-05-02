package com.ssafy.escapesvr.repository.querydsl;

import com.ssafy.escapesvr.dto.ThemeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InformationRepository {

    Page<ThemeResponseDto> findByConditions(String largeRegion, String smallRegion, String genre, Integer maxNumber, Integer maxLevel, Integer maxTime, Integer isSingleplay, Pageable pageable);

}
