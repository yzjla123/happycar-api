package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcMember;

@Repository
public interface MemberDao extends JpaRepository<HcMember, Integer>,JpaSpecificationExecutor<HcMember>{

	@Modifying
	@Query("update HcMember set isDeleted=1 where id=?")
	public int deleteById(Integer id);


	public List<HcMember> findByPhoneAndIsDeleted(String phone,int isDeleted);

	public List<HcMember> findByCoachId(Integer coachId);
	
	public List<HcMember> findByCoachIdAndProgressLessThan(Integer coachId,Integer progress);
}
