package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysRoleMenu;
@Repository
public interface SysRoleMenuDao extends JpaRepository<HcSysRoleMenu, Integer>,JpaSpecificationExecutor<HcSysRoleMenu>{

	@Query("delete from HcSysRoleMenu where roleId=?")
	@Modifying
	public int deleteByRoleId(Integer roleId);

	public List<HcSysRoleMenu> findByRoleId(Integer roleId);

	
}
