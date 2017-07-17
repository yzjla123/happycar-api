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
import com.happycar.api.model.HcKemu23Comment;


@Repository
public interface Kemu23CommentDao extends JpaRepository<HcKemu23Comment, Integer>,JpaSpecificationExecutor<HcKemu23Comment>{

	@Query(value="select * from hc_kemu23_comment where kemu23_id=? order by id desc limit 0,10",nativeQuery=true)
	List<HcKemu23Comment> findTop10ByKemu23Id(Integer id);

	Page<HcKemu23Comment> findByKemu23Id(Integer id, Pageable pageable);
	
}
