package com.ssafy.authsvr.controller;

import com.ssafy.authsvr.payload.request.UserPreferenceModifyReqeust;
import com.ssafy.authsvr.payload.request.UserProfilePreferenceRequest;
import com.ssafy.authsvr.payload.request.UserProfileRequest;
import com.ssafy.authsvr.payload.response.UserDetailProfileResponse;
import com.ssafy.authsvr.payload.response.UserProfileResponse;
import com.ssafy.authsvr.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로그인시 추가 정보 등록", notes = "회원정보 등록")
    @PostMapping("/recommend")
    public ResponseEntity<String> userRecommendInfoAdd(@RequestBody @Valid UserProfilePreferenceRequest userPreferenceRequest){
        log.info("userRecommendInfoAdd");

        if(ObjectUtils.isEmpty(userPreferenceRequest)){
            return ResponseEntity.notFound().build();
        }

        userService.AddRecommendInfoUser(userPreferenceRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("SUCCESS");
    }

    @ApiOperation(value = "프로필 회원정보 변경", notes = "회원정보 업데이트")
    @PutMapping("/recommend")
    public ResponseEntity<String> userRecommendInfoModify(@RequestPart(value = "profileRequest") @Valid UserProfileRequest profileRequest,
                                                          @RequestPart(value = "preferenceModifyRequest") @Valid UserPreferenceModifyReqeust preferenceModifyRequest,
                                                          @RequestPart(required = false) MultipartFile file){
        log.info("userRecommendInfoModify");

        if (ObjectUtils.isEmpty(profileRequest) || ObjectUtils.isEmpty(preferenceModifyRequest)) {
            return ResponseEntity.notFound().build();
        }

        userService.ModifyRecommendInfoUser(profileRequest,preferenceModifyRequest,file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("SUCCESS");
    }

    @ApiOperation(value = "회원의 닉네임 조회", notes = "회원 PK값으로 유저 닉네임 조회")
    @GetMapping("/name/{id}")
    public ResponseEntity<String> userFindNickName(@PathVariable(value = "id") Integer id) {
        log.info("userFindNickName");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findNickNameUser(id));
    }

    @ApiOperation(value = "회원 프로필 정보 조회", notes = "회원 PK값으로 프로필 정보 조회 - 이미지와 닉네임")
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileResponse> userFindProfile(@PathVariable("id") Integer id) {
        log.info("userFindProfile");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findProfileUser(id));
    }

    @ApiOperation(value = "회원 프로필 전체 조회", notes = "회원 PK값으로 프로필 전체 조회")
    @GetMapping("/allProfile/{id}")
    public ResponseEntity<UserDetailProfileResponse> userFindAllProfile(@PathVariable("id") Integer id) {
        log.info("userFindAllProfile");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAllProfileUser(id));
    }

    @ApiOperation(value = "회원 수 조회", notes = "회원 전체 수 조회")
    @GetMapping("/userCount")
    public ResponseEntity<Map<String,Integer>> userFindCount(){
        log.info("userFindCount");

        Map<String,Integer> map = new HashMap<>();
        map.put("userCount", userService.findCountUser());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }

    @ApiOperation(value = "회원 성별, 나이 조회", notes = "회원 성별 나이 조회")
    @GetMapping("/gender/{userId}")
    public ResponseEntity<Map<String,Object>> userFindGenderAge(@PathVariable Integer userId){
        log.info("userFindGenderAge");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findGenderAgeUser(userId));
    }
}