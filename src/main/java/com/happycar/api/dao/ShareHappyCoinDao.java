package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcAdvertisement;
import com.happycar.api.model.HcShareHappyCoin;

@Repository
public interface ShareHappyCoinDao extends JpaRepository<HcShareHappyCoin, Integer>,JpaSpecificationExecutor<HcShareHappyCoin>{

	@Query(value="select count(*) from hc_share_happy_coin where url=? and member_id=? and add_time>=CURDATE()",nativeQuery=true)
	int shareTime(String url, Integer memberId);

	
}
