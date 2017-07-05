package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcArticle;
import com.happycar.api.model.HcMessageCenter;


@Repository
public interface MessageCenterDao extends JpaRepository<HcMessageCenter, Integer>,JpaSpecificationExecutor<HcMessageCenter>{

	public List<HcMessageCenter> findByMemberIdAndIsReadAndIsDeleted(int memberId,int isRead, int isDeleted);
	
	@Query(value="select count(*) from hc_message_center where member_id=? and is_read=0 and is_deleted=0",nativeQuery=true)
	public Integer isUnreadMessage(int memberId);
	
	@Modifying
	@Query(value="update hc_message_center set is_read=1 where member_id=? and is_read=0 and is_deleted=0",nativeQuery=true)
	public int updateUnread(int memberId);

	public Page<HcMessageCenter> findByMemberIdAndIsDeleted(Integer memberId, int isDeleted, Pageable pageable);
	
}
