package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcArticle;


@Repository
public interface ArticleDao extends JpaRepository<HcArticle, Integer>,JpaSpecificationExecutor<HcArticle>{

	@Query(value="select new HcArticle(a.id,a.title,a.subjectId) from HcArticle a where subjectId=?")
	List<HcArticle> findBySubjectIdOrderByUpdateTimeDesc(Integer subjectId);
	
}
