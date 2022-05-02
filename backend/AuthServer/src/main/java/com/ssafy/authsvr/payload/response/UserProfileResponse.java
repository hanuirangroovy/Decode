package com.ssafy.authsvr.payload.response;

import com.ssafy.authsvr.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileResponse {
    private Integer userId;
    private String image;
    private String nickName;

    public static UserProfileResponse profileResponse(User user){
        return UserProfileResponse.builder()
                .userId(user.getId())
                .image(user.getImage())
                .nickName(user.getNickName())
                .build();
    }

    @Builder
    public UserProfileResponse(Integer userId, String image, String nickName) {
        this.userId = userId;
        this.image = image;
        this.nickName = nickName;
    }
}
