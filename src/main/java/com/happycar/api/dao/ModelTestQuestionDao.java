package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcModelTest;
import com.happycar.api.model.HcModelTestQuestion;


@Repository
public interface ModelTestQuestionDao extends JpaRepository<HcModelTestQuestion, Integer>,JpaSpecificationExecutor<HcModelTestQuestion>{

	
	
}
