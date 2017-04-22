package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcCoach;



@Repository
public interface BookDao extends JpaRepository<HcBook, Integer>,JpaSpecificationExecutor<HcBook>{

	public List<HcCoach> findByScheduleIdAndStatusIn(Integer scheduleId,List<Integer> status);

}
