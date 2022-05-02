package com.ssafy.authsvr.oauth.repository;

import com.ssafy.authsvr.oauth.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByTokenId(String tokenId);
    UserRefreshToken findByTokenIdAndRefreshToken(String tokenId, String refreshToken);
}
