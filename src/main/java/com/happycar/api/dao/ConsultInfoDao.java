package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcConsultInfo;

@Repository
public interface ConsultInfoDao extends JpaRepository<HcConsultInfo, Integer>,JpaSpecificationExecutor<HcConsultInfo>{

	@Modifying
	@Query("update HcConsultInfo set isDeleted=1 where id=?")
	public int deleteById(Integer id);
	
}
