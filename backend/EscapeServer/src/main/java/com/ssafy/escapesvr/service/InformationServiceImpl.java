package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.dto.StoreAndThemeResponseDto;
import com.ssafy.escapesvr.dto.ThemeResponseDto;
import com.ssafy.escapesvr.entity.Location;
import com.ssafy.escapesvr.entity.RecommendNumber;
import com.ssafy.escapesvr.entity.Store;
import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.repository.LocationRepository;
import com.ssafy.escapesvr.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InformationServiceImpl implements InformationService {

    @Autowired
    ThemeRepository themeRepo;

    @Autowired
    LocationRepository locationRepo;

    //테마아이디로 상세페이지
    @Override
    public StoreAndThemeResponseDto getDetail(Integer themeId) {
        //테마
        Theme theme=themeRepo.getById(themeId);
        //가게
        Store store=theme.getStore();

        RecommendNumber recommendNumber=theme.getRecommendNumber();
        StoreAndThemeResponseDto storeAndThemeResponseDto=new StoreAndThemeResponseDto(theme,store,recommendNumber);

        return storeAndThemeResponseDto;
    }
    //정보페이지 필터링
    @Override
    public Page<ThemeResponseDto> getInformationList(String largeRegion, String smallRegion, String genre, Integer maxNumber, Integer maxLevel, Integer maxTime,Integer isSingleplay, Pageable pageable) {

        Page<ThemeResponseDto> informations=null;

        informations=themeRepo.findByConditions(largeRegion,smallRegion,genre,maxNumber,maxLevel,maxTime,isSingleplay,pageable);

        return informations;

    }

    //smallRegion리스트 조회
    @Override
    public List<String> getSmallRegion(String largeRegion) {
       List<String> smallRegions=new ArrayList<>();
       if(largeRegion!=null){
           List<Location> locations=locationRepo.findAllByLargeRegion(largeRegion);
           for (Location location : locations) {
               smallRegions.add(location.getSmallRegion());
           }
       }else{
           List<Location> locations=locationRepo.findAll();
           for (Location location : locations) {
               smallRegions.add(location.getSmallRegion());
           }
       }
        return smallRegions;
    }


}
