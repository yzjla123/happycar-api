package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcActivityLog;
import com.happycar.api.model.HcArticle;
import com.happycar.api.model.HcBookOrder;


@Repository
public interface BookOrderDao extends JpaRepository<HcBookOrder, Integer>,JpaSpecificationExecutor<HcBookOrder>{

	List<HcBookOrder> findByStatus(int status);

}
