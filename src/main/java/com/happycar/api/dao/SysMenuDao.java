package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcSysMenu;

@Repository
public interface SysMenuDao extends JpaRepository<HcSysMenu, Integer>,JpaSpecificationExecutor<HcSysMenu>{

	@Query(value="select m.* from hc_sys_menu m "
			+ "left join hc_sys_role_menu rm on m.id=rm.menu_id "
			+ "left join hc_sys_role r on rm.role_id=r.id "
			+ "left join hc_sys_user_role ur on ur.user_id=r.user_id "
			+ "where ur.user_id=:userId and m.company_id=:companyId",nativeQuery=true)
	public List<HcSysMenu> getMenusByCompanyIdAndUserId(int companyId,int userId);

	public List<HcSysMenu> findByTypeAndLevelOrderBySeqAsc(int type, int level);

	@Query(value="select ifnull(max(seq),0) maxSeq from hc_sys_menu where pid=? and type=?",nativeQuery=true)
	public int findMaxSeqByPidAndType(int pid,int type);

	
	public List<HcSysMenu> getMenusByTypeOrderBySeqAsc(int type);

	public List<HcSysMenu> getMenusByTypeAndLevelOrderBySeqAsc(int type, int level);
	
	@Modifying
	@Query("delete from HcSysMenu where pid=?")
	public void deleteByPid(Integer id);

}
