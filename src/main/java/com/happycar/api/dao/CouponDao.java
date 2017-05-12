package com.happycar.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcCoupon;

@Repository
public interface CouponDao extends JpaRepository<HcCoupon, Integer>,JpaSpecificationExecutor<HcCoupon>{

	public HcCoupon findByMemberIdAndStatusAndMinMoneyLessThanOrderByValidDateAsc(int memberId, int status, Integer amount);
	
	@Modifying
	@Query(value="UPDATE HcCoupon SET status=2 where id=?")
	public void setCouponUsed(Integer couponId);

	public List<HcCoupon>  findByMemberIdAndStatusAndValidDateGreaterThanAndIsDeletedOrderByValidDateAsc(int memberId, int status, Date validDate, int isDeleted);
	public List<HcCoupon>  findByMemberIdAndStatusAndTypeAndValidDateGreaterThanAndIsDeletedOrderByValidDateAsc(int memberId, int status,int type, Date validDate, int isDeleted);

	public HcCoupon findByNoAndIsDeleted(String no, int isDeleted);


}
