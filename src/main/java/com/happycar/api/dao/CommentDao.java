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

	public List<HcComment> findByCoachIdAndIsDeleted(Integer coachId, int isDeleted);

	public HcComment findByBookIdAndIsDeleted(Integer bookId, int isDeleted);

	@Query("select avg(star) from HcComment where coachId=? and isDeleted=0")
	public Float findAvgStarByCoachId(Integer id);

	public Page<HcComment> findByCoachIdAndIsDeletedOrderByIdDesc(Integer coachId, int isDeleted, Pageable pageable);
	
}
