package com.ssafy.authsvr.service;

import com.ssafy.authsvr.entity.RecommendGenderAge;
import com.ssafy.authsvr.entity.RecommendGenre;
import com.ssafy.authsvr.entity.RecommendLike;
import com.ssafy.authsvr.payload.response.RecommendGenderAgeResponse;
import com.ssafy.authsvr.payload.response.RecommendGenreResponse;
import com.ssafy.authsvr.payload.response.RecommendLikeResponse;
import com.ssafy.authsvr.repository.RecommendGenderAgeRepository;
import com.ssafy.authsvr.repository.RecommendGenreRepository;
import com.ssafy.authsvr.repository.RecommendLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendServiceImpl implements RecommendService{

    private final RecommendGenreRepository recommendGenreRepository;
    private final RecommendLikeRepository recommendLikeRepository;
    private final RecommendGenderAgeRepository recommendGenderAgeRepository;

    @Override
    public RecommendLikeResponse findRecommendLike(Integer userId) {
        Optional<RecommendLike> recommendLikeOptional = recommendLikeRepository.findByUserId(userId);
        RecommendLike recommendLike = recommendLikeOptional.orElseThrow(NoSuchElementException::new);

        return RecommendLikeResponse.recommendLikeResponseBuilder(recommendLike);
    }

    @Override
    public RecommendGenderAgeResponse findRecommendGenderAge(Integer userId) {
        Optional<RecommendGenderAge> recommendGenderAgeOptional = recommendGenderAgeRepository.findByUserId(userId);
        RecommendGenderAge recommendGenderAge = recommendGenderAgeOptional.orElseThrow(NoSuchElementException::new);

        return RecommendGenderAgeResponse.recommendGenderAgeResponseBuilder(recommendGenderAge);
    }

    @Override
    public RecommendGenreResponse findRecommendGenre(Integer userId) {
        Optional<RecommendGenre> recommendGenreOptional = recommendGenreRepository.findByUserId(userId);
        RecommendGenre recommendGenre = recommendGenreOptional.orElseThrow(NoSuchElementException::new);

        return RecommendGenreResponse.recommendGenreResponseBuilder(recommendGenre);
    }
}
