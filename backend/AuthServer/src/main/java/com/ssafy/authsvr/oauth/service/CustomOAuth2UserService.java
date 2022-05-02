package com.ssafy.authsvr.oauth.service;

import com.ssafy.authsvr.entity.GenrePreference;
import com.ssafy.authsvr.entity.User;
import com.ssafy.authsvr.oauth.domain.ProviderType;
import com.ssafy.authsvr.oauth.domain.RoleType;
import com.ssafy.authsvr.oauth.domain.UserPrincipal;
import com.ssafy.authsvr.oauth.exception.OAuthProviderMissMatchException;
import com.ssafy.authsvr.oauth.info.OAuth2UserInfo;
import com.ssafy.authsvr.oauth.info.OAuth2UserInfoFactory;
import com.ssafy.authsvr.repository.GenrePreferenceRepository;
import com.ssafy.authsvr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final GenrePreferenceRepository genrePreferenceRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByTokenId(userInfo.getId());

        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                        " account. Please use your " + savedUser.getProviderType() + " account to login."
                );
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);

            Optional<User> genreUsers = userRepository.findById(savedUser.getId());
            User genreUser = genreUsers.orElseThrow(NoSuchElementException::new);

            GenrePreference genrePreference = new GenrePreference(
                    0,0,0,0,0,0,0,0,0,0,genreUser);

            GenrePreference genre = genrePreferenceRepository.save(genrePreference);
            genreUser.setGenrePreference(genre);
            userRepository.save(genreUser);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();

        User user = new User(
                Integer.valueOf(RandomStringUtils.randomNumeric(8)),
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getImageUrl(),
                providerType,
                RoleType.USER,
                now,
                now
        );

        return userRepository.saveAndFlush(user);
    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getName().equals(userInfo.getName())) {
            user.setUserNameModified(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getImage().equals(userInfo.getImageUrl())) {
            user.setUserProfileImageModified(userInfo.getImageUrl());
        }

        return user;
    }
}
