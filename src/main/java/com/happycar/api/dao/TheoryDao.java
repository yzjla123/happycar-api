package com.happycar.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcTheory;

@Repository
public interface TheoryDao extends JpaRepository<HcTheory, Integer>,JpaSpecificationExecutor<HcTheory>{
	
	@Modifying
	@Query("update HcTheory set isDeleted=1 where id=?")
	public int deleteById(Integer id);
	
	public List<HcTheory> findBySubjectTypeAndUpdateTimeGreaterThanAndIsDeletedOrderBySeqAsc(int subjectType,Date updateTime,int isDeleted);
	
	public List<HcTheory> findBySubjectTypeAndIsDeletedOrderBySeqAsc(int subjectType,int isDeleted);

	@Query("select max(updateTime) from HcTheory")
	public Date findMaxUpdateTime();

}
