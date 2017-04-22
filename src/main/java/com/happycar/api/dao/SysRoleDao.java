package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysRole;
@Repository
public interface SysRoleDao extends JpaRepository<HcSysRole, Integer>,JpaSpecificationExecutor<HcSysRole>{

	public List<HcSysRole> findAllByIsDeleted(int isDeleted);

	public HcSysRole findByNameAndIsDeleted(String name,int isDeleteed);

}
