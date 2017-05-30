package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcUpdate;

@Repository
public interface UpdateDao extends JpaRepository<HcUpdate, Integer>,JpaSpecificationExecutor<HcUpdate>{

	@Modifying
	@Query("update HcUpdate set isDeleted=1 where id=?")
	public int deleteById(Integer id);

	@Query(value="select * from hc_update where type=? order by version desc limit 1",nativeQuery=true)
	public HcUpdate findMaxVersionByType(String type);
	
}
