package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcMemberRelation;


@Repository
public interface MemberRelationDao extends JpaRepository<HcMemberRelation, Integer>,JpaSpecificationExecutor<HcMemberRelation>{

	public HcMemberRelation findByCidAndLevel(Integer cid, int level);
	
	public List<HcMemberRelation> findByPidAndLevel(Integer pid, int level);

	@Modifying
	@Query("delete from HcMemberRelation where cid=?")
	public void deleteByCid(int memberId);

	public List<HcMemberRelation> findByCid(int memberId);

}
