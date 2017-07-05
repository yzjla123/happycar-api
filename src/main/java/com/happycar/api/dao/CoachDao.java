package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcCoach;

@Repository
public interface CoachDao extends JpaRepository<HcCoach, Integer>,JpaSpecificationExecutor<HcCoach>{

	public List<HcCoach> findBySchoolId(Integer id);

	@Modifying
	@Query("update HcCoach set isDeleted=1 where id=?")
	public int deleteById(Integer id);

	public List<HcCoach> findByPhone(String phone);

	public List<HcCoach> findAllByIsDeleted(int isDeleted);

	public List<HcCoach> findByPhoneAndIsDeleted(String phone, int isDeleted);

	@Modifying
	@Query("update HcCoach set balance=balance+? where id=?")
	public int addAmount(float amount,int coachId);
	
	@Modifying
	@Query("update HcCoach set balance=balance-? where id=?")
	public int reduceAmount(float amount,int coachId);
}
