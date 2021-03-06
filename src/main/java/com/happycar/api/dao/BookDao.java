package com.happycar.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcCoach;



@Repository
public interface BookDao extends JpaRepository<HcBook, Integer>,JpaSpecificationExecutor<HcBook>{

	public List<HcCoach> findByScheduleIdAndStatusIn(Integer scheduleId,List<Integer> status);

	@Modifying
	@Query(value="delete from HcBook where date=?")
	public void deleteByDate(Date date);

	public List<HcBook> findByMemberIdAndDate(Integer memberId, Date date);
	
	public List<HcBook> findByMemberIdAndDateIn(Integer memberId, List<Date> date);

	public List<HcBook> findByCoachIdAndDate(Integer coachId, Date date);
	
	public List<HcBook> findByMemberIdAndSubjectType(Integer memberId,Integer subjectType);

	@Modifying
	@Query(value="delete from hc_book where book_order_id=?",nativeQuery=true)
	public int deleteByBookOrderId(int bookOrderId);

	public List<HcBook> findByBookOrderId(Integer bookOrderId);

}
