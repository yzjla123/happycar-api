package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcModelTest;


@Repository
public interface ModelTestDao extends JpaRepository<HcModelTest, Integer>,JpaSpecificationExecutor<HcModelTest>{

	@Query(value = "select max(score) from hc_model_test where member_id=? and is_deleted=0",nativeQuery=true)
	public Integer findMaxScoreByMemberId(Integer id);

	
	
}
