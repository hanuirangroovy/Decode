package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.StoreAndThemeResponseDto;
import com.ssafy.escapesvr.dto.ThemeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface InformationService {
    StoreAndThemeResponseDto getDetail(Integer themeId);
    Page<ThemeResponseDto> getInformationList(String largeRegion, String smallRegion, String genre, Integer maxNumber, Integer maxLevel, Integer maxTime,Integer isSingleplay, Pageable pageable);
    List<String> getSmallRegion(String largeRegion);
}
