package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysUserRole;
@Repository
public interface SysUserRoleDao extends JpaRepository<HcSysUserRole, Integer>,JpaSpecificationExecutor<HcSysUserRole>{

	@Query(value="delete from HcSysUserRole where userId=?")
	@Modifying
	public void deleteByUserId(Integer id);

	public List<HcSysUserRole> findByUserId(int userId);
}
