package com.ssafy.authsvr.oauth.domain;

import lombok.Getter;

@Getter
public enum ProviderType {
    GOOGLE,
    NAVER,
    KAKAO,
    LOCAL;
}
