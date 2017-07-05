package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcPosition;


@Repository
public interface PositionDao extends JpaRepository<HcPosition, Integer>,JpaSpecificationExecutor<HcPosition>{

	@Modifying
	@Query(value="delete from hc_position where rid=? and type=?",nativeQuery=true)
	public int deleteByRidAndType(int rid,String type);
}
