package com.happycar.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcCommissionLog;


@Repository
public interface CommissionLogDao extends JpaRepository<HcCommissionLog, Integer>,JpaSpecificationExecutor<HcCommissionLog>{

	Page<HcCommissionLog> findByPidAndStatusOrderByIdDesc(Integer id, int status, Pageable pageable);

}
