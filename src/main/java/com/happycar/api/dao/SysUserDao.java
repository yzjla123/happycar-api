package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysUser;
@Repository
public interface SysUserDao extends JpaRepository<HcSysUser, Integer>,JpaSpecificationExecutor<HcSysUser>{

	public HcSysUser findByUserNameAndIsDeleted(String userName, int isDeleted);

	@Modifying
	@Query("update HcSysUser set status=1 where id=?")
	public int disableUserById(int id);

	@Modifying
	@Query("update HcSysUser set status=0 where id=?")
	public int enableUserById(int id);

	@Query(value="update HcSysUser set isDeleted=1 where id=?")
	@Modifying
	public void deleteById(int id);
}
