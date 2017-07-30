package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcArticle;
import com.happycar.api.model.HcLearnProgress;


@Repository
public interface LearnProgressDao extends JpaRepository<HcLearnProgress, Integer>,JpaSpecificationExecutor<HcLearnProgress>{

	HcLearnProgress findByMemberId(Integer id);

	
}
