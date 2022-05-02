package com.ssafy.authsvr.repository;

import com.ssafy.authsvr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByTokenId(String tokenId);

    Integer countAllBy();
}