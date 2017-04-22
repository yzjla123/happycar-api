package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSignup;

@Repository
public interface SignupDao extends JpaRepository<HcSignup, Integer>,JpaSpecificationExecutor<HcSignup>{

	public List<HcSignup> findByMemberId(Integer id);

	@Modifying
	@Query("update HcSignup set isDeleted=1 where id=?")
	public int deleteById(Integer id);

}
