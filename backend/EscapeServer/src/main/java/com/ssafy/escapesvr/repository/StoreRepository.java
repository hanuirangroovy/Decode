package com.ssafy.escapesvr.repository;


import com.ssafy.escapesvr.entity.Store;
import com.ssafy.escapesvr.repository.querydsl.InformationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Integer>, InformationRepository {

}
