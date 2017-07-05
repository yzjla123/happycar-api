package com.happycar.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcCoachWithdraw;
import com.happycar.api.model.HcCommissionWithdraw;


@Repository
public interface CoachWithdrawDao extends JpaRepository<HcCoachWithdraw, Integer>,JpaSpecificationExecutor<HcCoachWithdraw>{

	public Page<HcCoachWithdraw> findByCoachIdOrderByIdDesc(Integer coachId, Pageable pageable);
	
	@Query(value="select * from hc_coach_withdraw where coach_id=? order by id desc limit 1",nativeQuery=true)
	public HcCoachWithdraw findByCoachIdOrderByIdDesc(Integer coachId);

}
