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
import com.happycar.api.model.HcComment;


@Repository
public interface CommentDao extends JpaRepository<HcComment, Integer>,JpaSpecificationExecutor<HcComment>{

	@Query(value = "select * from hc_comment where coach_id=?1 and is_deleted=0 order by id desc limit 0,3",nativeQuery=true)
	public List<HcComment> findByCoachIdAndIsDeletedOrderByIdDesc(Integer coachId);

	public HcComment findByBookIdAndIsDeleted(Integer bookId, int isDeleted);

	@Query("select avg(star) from HcComment where coachId=? and isDeleted=0")
	public Float findAvgStarByCoachId(Integer id);

	public Page<HcComment> findByCoachIdAndIsDeletedOrderByIdDesc(Integer coachId, int isDeleted, Pageable pageable);
	
}
