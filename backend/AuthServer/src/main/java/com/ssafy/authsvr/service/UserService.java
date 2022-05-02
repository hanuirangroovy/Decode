package com.ssafy.authsvr.service;

import com.ssafy.authsvr.payload.request.UserPreferenceModifyReqeust;
import com.ssafy.authsvr.payload.request.UserProfilePreferenceRequest;
import com.ssafy.authsvr.payload.request.UserProfileRequest;
import com.ssafy.authsvr.payload.response.UserDetailProfileResponse;
import com.ssafy.authsvr.payload.response.UserProfileResponse;
import com.ssafy.authsvr.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface UserService {

    void AddRecommendInfoUser(UserProfilePreferenceRequest userPreferenceRequest);

    void ModifyRecommendInfoUser(UserProfileRequest profileRequest, UserPreferenceModifyReqeust preferenceModifyRequest,
                                 MultipartFile file);

    User findDetailsUser(String tokenId);

    String findNickNameUser(Integer userId);

    UserProfileResponse findProfileUser(Integer userId);

    UserDetailProfileResponse findAllProfileUser(Integer userId);

    Integer findCountUser();

    Map<String,Object> findGenderAgeUser(Integer userId);
}
