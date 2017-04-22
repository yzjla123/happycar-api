package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysParam;
@Repository
public interface SysParamDao extends JpaRepository<HcSysParam, Integer>,JpaSpecificationExecutor<HcSysParam>{

	public HcSysParam findByKey(String key);


}
