package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysParam;

@Repository
public interface SysParamDao extends JpaRepository<HcSysParam, Integer>,JpaSpecificationExecutor<HcSysParam>{

	public HcSysParam findByCode(String code);
	
	public HcSysParam findByCodeAndPid(String code,Integer pid);

	public List<HcSysParam> findByPidOrderBySeqAsc(Integer pid);
	
	@Query(value="select * from hc_sys_param where pid=(select id from hc_sys_param where code=?)",nativeQuery=true)
	public List<HcSysParam> findByPCodeOrderBySeqAsc(String pCode);
	
}
