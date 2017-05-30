package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcPushInfo;


@Repository
public interface PushInfoDao extends JpaRepository<HcPushInfo, Integer>,JpaSpecificationExecutor<HcPushInfo>{

	@Modifying
	@Query(value="delete from HcPushInfo where phone=? and type=?")
	public void deleteByPhoneAndType(String phone, String type);

}
