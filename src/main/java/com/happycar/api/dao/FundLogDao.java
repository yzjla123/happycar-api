package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcFundLog;


@Repository
public interface FundLogDao extends JpaRepository<HcFundLog, Integer>,JpaSpecificationExecutor<HcFundLog>{


}
