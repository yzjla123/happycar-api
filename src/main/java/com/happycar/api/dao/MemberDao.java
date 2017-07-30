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

	public List<HcMember> findAllByCoachIdAndProgressLessThanAndIsDeleted(int coachId,int progress,int isDeleted);

	@Modifying
	@Query("update HcMember set commission=commission+? where id=?")
	public int addCommission(float commission,int memberId);
	
	@Modifying
	@Query("update HcMember set commission=commission-? where id=?")
	public int reduceCommission(float commission,int memberId);

	@Modifying
	@Query("update HcMember set happy_coin=happy_coin+? where id=?")
	public int addHappyCoin(float happyCoin,int memberId);
	
	@Modifying
	@Query("update HcMember set happy_coin=happy_coin-? where id=?")
	public int reduceHappyCoin(float happyCoin,int memberId);
}
