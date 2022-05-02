package com.ssafy.authsvr.service;

import com.ssafy.authsvr.payload.response.RecommendGenderAgeResponse;
import com.ssafy.authsvr.payload.response.RecommendGenreResponse;
import com.ssafy.authsvr.payload.response.RecommendLikeResponse;

public interface RecommendService {

    RecommendLikeResponse findRecommendLike(Integer userId);
    RecommendGenderAgeResponse findRecommendGenderAge(Integer userId);
    RecommendGenreResponse findRecommendGenre(Integer userId);
}
