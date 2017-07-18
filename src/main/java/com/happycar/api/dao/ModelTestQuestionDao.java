package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcModelTest;
import com.happycar.api.model.HcModelTestQuestion;


@Repository
public interface ModelTestQuestionDao extends JpaRepository<HcModelTestQuestion, Integer>,JpaSpecificationExecutor<HcModelTestQuestion>{

	@Query("select distinct theoryId from HcModelTestQuestion where modelTestId in ?1 ")
	public List<Object> findDoneTheoryIds(List<Integer> modelTestIds);

	
	
}
