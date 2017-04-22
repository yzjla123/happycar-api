package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcPayOrder;

@Repository
public interface PayOrderDao extends JpaRepository<HcPayOrder, Integer>,JpaSpecificationExecutor<HcPayOrder>{

	public HcPayOrder findByOrderNoAndStatus(String orderNo, int status);

	
}
