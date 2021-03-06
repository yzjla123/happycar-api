package com.happycar.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSchedule;

@Repository
public interface ScheduleDao extends JpaRepository<HcSchedule, Integer>,JpaSpecificationExecutor<HcSchedule>{

	public List<HcSchedule> findByCoachIdAndDateAndSubjectTypeAndDrivingLicenseTypeOrderByTime1Asc(Integer coachId,Date date,Integer subjectType,String drivingLicenseType);

	@Query(value="select max(date) from HcSchedule where coachId=? and subjectType=? and drivingLicenseType=?")
	public Date findLastScheduleDateByCoachIdAndDateAndSubjectTypeAndDrivingLicenseType(Integer coachId, Integer subjectType,String drivingLicenseType);
	
	@Query(value="select s.date from HcSchedule s where s.date between ? and ? and coachId=? and subjectType=? and bookNum>0 and drivingLicenseType=?")
	public List<Date> findBookDate(Date start,Date end,Integer coachId,Integer subjectType,String drivingLicenseType);

	@Modifying
	@Query(value="delete from HcSchedule where subjectType=? and date between ? and ? and drivingLicenseType=?")
	public int deleteBySubjectTypeAndDateBetweenBetweenAndDrivingLicenseType(Integer subjectType,Date from, Date to,String drivingLicenseType);

	@Query(value="from HcSchedule where bookNum>=memberNum and id in ?1")
	public List<HcSchedule> findFullBookByIdIn(List<Integer> scheduleIdArray);
	
	public List<HcSchedule> findByIdIn(List<Integer> scheduleIdArray);

}
