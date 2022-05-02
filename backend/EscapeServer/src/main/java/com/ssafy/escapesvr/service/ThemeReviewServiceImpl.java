package com.ssafy.escapesvr.service;

import com.netflix.discovery.converters.Auto;
import com.ssafy.escapesvr.client.UserServiceClient;
import com.ssafy.escapesvr.dto.MyReviewResponseDto;
import com.ssafy.escapesvr.dto.PosterResponseDto;
import com.ssafy.escapesvr.dto.ReviewRequestDto;
import com.ssafy.escapesvr.dto.ThemeReviewResponseDto;
import com.ssafy.escapesvr.entity.Theme;
import com.ssafy.escapesvr.entity.ThemeReview;
import com.ssafy.escapesvr.entity.ThemeReviewDocument;
import com.ssafy.escapesvr.repository.ThemeRepository;
import com.ssafy.escapesvr.repository.ThemeReviewDocumentRepository;
import com.ssafy.escapesvr.repository.ThemeReviewRepository;
import feign.FeignException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ThemeReviewServiceImpl implements ThemeReviewService{

    @Autowired
    ThemeRepository themeRepo;

    @Autowired
    ThemeReviewRepository themeReviewRepo;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    ThemeReviewDocumentRepository themeReviewDocumentRepository;

    //테마의 리뷰리스트
    @Override
    public Page<ThemeReviewResponseDto> getThemeReviewList(Integer themeId, Pageable pageable) {

        Theme theme=themeRepo.getById(themeId);
        Page<ThemeReview>reviews=themeReviewRepo.findByTheme(theme,pageable);
        Page<ThemeReviewResponseDto> themelist=reviews.map(
                o-> new ThemeReviewResponseDto(o.getId(),o.getUserId(),o.getUserNickName(),o.getMyScore(),o.getReviewContent(),o.getCreatedAt(),o.getClearTime()));
        return themelist;
    }

    //auth로 전달- 내가 작성한 리뷰리스트
    @Override
    public Page<MyReviewResponseDto> getMyReviewList(Integer userId, Pageable pageable) {
        List<ThemeReview>reviews=themeReviewRepo.findAllByUserId(userId,pageable);
        List<MyReviewResponseDto> myReviewResponseDtos=new ArrayList<>();
        List<ThemeReview>findcnts=themeReviewRepo.findAllByUserId(userId);
        int cnt=findcnts.size();
        for (ThemeReview review : reviews) {
            Theme theme=review.getTheme();
            MyReviewResponseDto reviewDto=new MyReviewResponseDto(review,theme);
            myReviewResponseDtos.add(reviewDto);
        }
        return new PageImpl<>(myReviewResponseDtos, pageable,cnt);
    }


    // 포스터
    @Override
    public Page<PosterResponseDto> getPosters(Integer userId, Pageable pageable) {
        List<ThemeReview>reviews=themeReviewRepo.findAllByUserId(userId,pageable);
        List<ThemeReview>findcnts=themeReviewRepo.findAllByUserId(userId);
        int cnt=findcnts.size();
        List<PosterResponseDto> posters=new ArrayList<>();

        Map<Integer,Integer>ids=new HashMap<>();

        for (ThemeReview review : reviews) {
            Theme theme=review.getTheme();

            Integer id=theme.getId();
            if(!ids.containsKey(id)){
                PosterResponseDto posterResponseDto=new PosterResponseDto(theme);
                posters.add(posterResponseDto);
                ids.put(id,1);
            }else{
                continue;
            }
        }

        return new PageImpl<>(posters, pageable,cnt);
    }



    //내가깬장르
    @Override
    public int[] getMyGenre(Integer userId) {
        List<ThemeReview>reviews=themeReviewRepo.findAllByUserId(userId);
//        스릴러 로맨스 추리 SF/판타지 모험/액션  코미디 범죄  공포 19금 감성/드라마

        int[]genre=new int[10];

        Map<Integer,Integer>ids=new HashMap<>();
        for (ThemeReview review : reviews) {
            Theme theme=review.getTheme();
            // 테마의 아이디
            Integer id=theme.getId();
            if(!ids.containsKey(id)){
                ids.put(id,1);
               if(theme.getGenre().equals("스릴러")){
                   genre[0]+=1;
               }else if(theme.getGenre().equals("로맨스")){
                   genre[1]+=1;
               }else if(theme.getGenre().equals("추리")) {
                   genre[2] += 1;
               }else if(theme.getGenre().equals("SF/판타지")){
                   genre[3]+=1;
               }else if(theme.getGenre().equals("모험/액션")){
                   genre[4]+=1;
               }else if(theme.getGenre().equals("코미디")){
                   genre[5]+=1;
               }else if(theme.getGenre().equals("범죄")){
                   genre[6]+=1;
               }else if(theme.getGenre().equals("공포")){
                   genre[7]+=1;
               }else if(theme.getGenre().equals("19금")){
                   genre[8]+=1;
               }else if(theme.getGenre().equals("감성/드라마")){
                   genre[9]+=1;
               }
            }else{
                continue;
            }
        }
        return genre;
    }
    
    //리뷰 작성
    @Override
    @Transactional
    public void insertReview(ReviewRequestDto reviewRequestDto) {
        ThemeReview themeReview=new ThemeReview();
        ThemeReviewDocument themeReviewDocument=new ThemeReviewDocument();
        String gender="";
        Integer age=null;
        Theme theme=themeRepo.getById(reviewRequestDto.getThemeId());

        themeReview.setUserId(reviewRequestDto.getUserId());

        try{
            String nickname = userServiceClient.userFindNickName(reviewRequestDto.getUserId());
            Map<String,Object> maps=userServiceClient.userFindGenderAge(reviewRequestDto.getUserId());
            Object genders=maps.get("gender");
            Object ages=maps.get("age");
            gender=genders.toString();
            age=(Integer)ages;
            themeReview.setUserNickName(nickname);
        }catch (FeignException e){
            e.printStackTrace();
        }

        themeReview.setMyScore(reviewRequestDto.getMyScore());
        themeReview.setClearTime(reviewRequestDto.getClearTime());
        themeReview.setReviewContent(reviewRequestDto.getReviewContent());
        themeReview.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        //리뷰 개수는 1 증가
        theme.setReviewCnt(theme.getReviewCnt()+1);
        //테마 score 재계산
        theme.setScore((theme.getScore()*theme.getReviewCnt()+ reviewRequestDto.getMyScore())/(theme.getReviewCnt()+1));
        themeReview.setTheme(theme);


        themeReviewDocument.setThemeId(theme.getId());

        themeReviewDocument.setUserId(reviewRequestDto.getUserId());
        themeReviewDocument.setRating(reviewRequestDto.getMyScore());
        themeReviewDocument.setGender(gender);
        themeReviewDocument.setAge(age);

        //저장
        themeReviewRepo.save(themeReview);
        themeReviewDocumentRepository.save(themeReviewDocument);
        themeRepo.save(theme);

        themeReviewDocument.setReviewId(themeReview.getId());
        themeReviewDocumentRepository.save(themeReviewDocument);
    }

    //리뷰 삭제
    @Override
    @Transactional
    public void deleteReview(Integer themeReviewId) {
        ThemeReview themeReview=themeReviewRepo.getById(themeReviewId);
        Theme theme=themeReview.getTheme();

        //테마의 리뷰 되돌려놓기
        theme.setReviewCnt(theme.getReviewCnt()-1);
        theme.setScore((theme.getScore()*theme.getReviewCnt()- themeReview.getMyScore())/(theme.getReviewCnt()-1));

        //리뷰 지우기
        themeReviewRepo.deleteById(themeReviewId);
        themeReviewDocumentRepository.deleteByReviewId(themeReviewId);
        //저장
        themeRepo.save(theme);

    }


}
