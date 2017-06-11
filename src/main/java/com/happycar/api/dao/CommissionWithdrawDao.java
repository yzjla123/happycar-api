package com.happycar.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcCommissionWithdraw;


@Repository
public interface CommissionWithdrawDao extends JpaRepository<HcCommissionWithdraw, Integer>,JpaSpecificationExecutor<HcCommissionWithdraw>{

	public Page<HcCommissionWithdraw> findByMemberIdOrderByIdDesc(Integer memberId, Pageable pageable);
	
	@Query(value="select * from hc_commission_withdraw where member_id=? order by id desc limit 1",nativeQuery=true)
	public HcCommissionWithdraw findByMemberIdOrderByIdDesc(Integer memberId);

}
