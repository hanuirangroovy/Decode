package com.ssafy.authsvr.entity;

import com.ssafy.authsvr.oauth.domain.ProviderType;
import com.ssafy.authsvr.oauth.domain.RoleType;
import com.ssafy.authsvr.payload.request.UserProfilePreferenceRequest;
import com.ssafy.authsvr.payload.request.UserProfileRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private Integer id;

    private String email;

    private String image;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String tokenId;

    private String nickName;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String gender;

    private Integer age;

    private String largeRegion;

    private String smallRegion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_preference_id")
    private GenrePreference genrePreference;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RecommendLike recommendLike;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RecommendGenderAge recommendGenderAge;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RecommendGenre recommendGenre;

    public User(Integer id, String tokenId, String name, String email, String image, ProviderType providerType,
                RoleType roleType, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.tokenId = tokenId;
        this.name = name;
        this.email = email == null ? "NO_EMAIL" : email;
        this.image = image;
        this.providerType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.nickName = name;
        this.modifiedAt = modifiedAt;
    }

    public void setUserProfileImageModified(String image) {
        this.image = image;
    }

    public void setUserNameModified(String name) {
        this.name = name;
    }

    public void setUserGenreInfoAdd(UserProfilePreferenceRequest profileRequest, GenrePreference genrePreference) {
        this.nickName = profileRequest.getNickName();
        this.age = profileRequest.getAge();
        this.gender = profileRequest.getGender();
        this.largeRegion = profileRequest.getLargeRegion();
        this.smallRegion = profileRequest.getSmallRegion();
        this.genrePreference = genrePreference;
    }

    public void setUserGenreInfoModify(UserProfileRequest profileRequest, GenrePreference genrePreference,String image) {
        this.nickName = profileRequest.getNickName();
        this.age = profileRequest.getAge();
        this.largeRegion = profileRequest.getLargeRegion();
        this.smallRegion = profileRequest.getSmallRegion();
        this.genrePreference = genrePreference;
        this.image = image;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setGenrePreference(GenrePreference genrePreference){
        this.genrePreference = genrePreference;
    }

}