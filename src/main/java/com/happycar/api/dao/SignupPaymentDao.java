package com.happycar.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcFundLog;
import com.happycar.api.model.HcSignupPayment;


@Repository
public interface SignupPaymentDao extends JpaRepository<HcSignupPayment, Integer>,JpaSpecificationExecutor<HcSignupPayment>{


}
