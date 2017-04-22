package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSchedule;

@Repository
public interface ScheduleDao extends JpaRepository<HcSchedule, Integer>,JpaSpecificationExecutor<HcSchedule>{

	public List<HcSchedule> findByCoachId(Integer id);

}
