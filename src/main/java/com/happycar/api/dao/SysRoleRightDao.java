package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysRoleRight;
@Repository
public interface SysRoleRightDao extends JpaRepository<HcSysRoleRight, Integer>,JpaSpecificationExecutor<HcSysRoleRight>{

	@Query("delete from HcSysRoleRight where roleId=?")
	@Modifying
	public int deleteByRoleId(Integer roleId);

	public List<HcSysRoleRight> findByRoleId(Integer roleId);


	
}
