package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysRight;
@Repository
public interface SysRightDao extends JpaRepository<HcSysRight, Integer>,JpaSpecificationExecutor<HcSysRight>{


	public List<HcSysRight> findByLevelOrderBySeqAsc(int level);

	@Query(value="select ifnull(max(seq),0) maxSeq from hc_sys_right where pid=? ",nativeQuery=true)
	public int findMaxSeqByPid(int pid);
	

	public List<HcSysRight> getRightsByLevelOrderBySeqAsc(int level);

	public HcSysRight findByCode(String code);
	
}
