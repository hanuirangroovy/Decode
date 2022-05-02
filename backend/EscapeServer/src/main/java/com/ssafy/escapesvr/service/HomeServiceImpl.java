package com.ssafy.escapesvr.service;

import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.repository.StoreRepository;
import com.ssafy.escapesvr.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService{

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public int[] getCount() {

        int[]count=new int[2];

        int themeSize=themeRepository.findAll().size();
        int storeSize=storeRepository.findAll().size();

        count[0]=themeSize;
        count[1]=storeSize;

        return count;
    }

    @Override
    public List<Integer> getNonMember() {
        List<Theme> themes =themeRepository.findTop6ByReviewCntGreaterThanEqualOrderByScoreDesc(500);
        List<Integer>idList=new ArrayList<>();
        for (Theme theme : themes) {
            idList.add(theme.getId());
        }
        return idList;
    }
}
