package com.happycar.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcCoachFundLog;
import com.happycar.api.model.HcFundLog;


@Repository
public interface CoachFundLogDao extends JpaRepository<HcCoachFundLog, Integer>,JpaSpecificationExecutor<HcCoachFundLog>{

	Page<HcCoachFundLog> findByCidOrderByIdDesc(Integer cid, Pageable pageable);


}
