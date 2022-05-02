package com.ssafy.authsvr.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.authsvr.entity.GenrePreference;
import com.ssafy.authsvr.payload.request.UserPreferenceModifyReqeust;
import com.ssafy.authsvr.payload.request.UserProfilePreferenceRequest;
import com.ssafy.authsvr.payload.request.UserProfileRequest;
import com.ssafy.authsvr.payload.response.UserDetailProfileResponse;
import com.ssafy.authsvr.payload.response.UserProfileResponse;
import com.ssafy.authsvr.entity.User;
import com.ssafy.authsvr.repository.GenrePreferenceRepository;
import com.ssafy.authsvr.repository.PreferenceDocumentRepository;
import com.ssafy.authsvr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GenrePreferenceRepository genrePreferenceRepository;
    private final PreferenceDocumentRepository preferenceDocumentRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String url;

    private final AmazonS3 amazonS3;

    @Override
    @Transactional
    public void AddRecommendInfoUser(UserProfilePreferenceRequest userPreferenceRequest) {

        Optional<User> userOptional = userRepository.findById(userPreferenceRequest.getId());
        User user = userOptional.orElseThrow(NoSuchElementException::new);
        GenrePreference genrePreference = GenrePreference.genrePreferenceBuild(userPreferenceRequest,user);
        GenrePreference genre = genrePreferenceRepository.save(genrePreference);

        user.setUserGenreInfoAdd(userPreferenceRequest,genre);
    }

    @Override
    @Transactional
    public void ModifyRecommendInfoUser(UserProfileRequest profileRequest, UserPreferenceModifyReqeust preferenceModifyRequest,
                                        MultipartFile file) {

        Optional<User> userOptional = userRepository.findById(profileRequest.getId());
        User user = userOptional.orElseThrow(NoSuchElementException::new);

        Optional<GenrePreference> genrePreferences = genrePreferenceRepository.findById(preferenceModifyRequest.getId());
        GenrePreference genrePreference = genrePreferences.orElseThrow(NoSuchElementException::new);
        genrePreference.setGenrePreferenceInfoModified(preferenceModifyRequest,user);

        String imageUrl;
        if(!file.isEmpty()){
            imageUrl = AwsFile(file);
        }else {
            imageUrl = user.getImage();
        }

        user.setUserGenreInfoModify(profileRequest,genrePreference, imageUrl);
    }

    @Override
    public User findDetailsUser(String tokenId) {

        return userRepository.findByTokenId(tokenId);
    }


    @Override
    public String findNickNameUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(NoSuchElementException::new).getNickName();
    }

    @Override
    public UserProfileResponse findProfileUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return UserProfileResponse.profileResponse(userOptional.orElseThrow(NoSuchElementException::new));
    }

    @Override
    public UserDetailProfileResponse findAllProfileUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(NoSuchElementException::new);
        Optional<GenrePreference> genrePreferences = genrePreferenceRepository.findById(user.getGenrePreference().getId());
        GenrePreference genrePreference = genrePreferences.orElseThrow(NoSuchElementException::new);

        return UserDetailProfileResponse.allProfileResponse(user,genrePreference);
    }

    @Override
    public Integer findCountUser() {
        return userRepository.countAllBy() + preferenceDocumentRepository.countAllBy();
    }

    @Override
    public Map<String,Object> findGenderAgeUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(NoSuchElementException::new);

        Map<String,Object> map = new HashMap<>();
        map.put("gender",user.getGender());
        map.put("age",user.getAge());

        return map;
    }

    private String AwsFile(MultipartFile file) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }

        return String.format(url +  "/%s", fileName);
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}