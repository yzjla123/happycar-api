package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSchool;

@Repository
public interface SchoolDao extends JpaRepository<HcSchool, Integer>,JpaSpecificationExecutor<HcSchool>{

	


}
