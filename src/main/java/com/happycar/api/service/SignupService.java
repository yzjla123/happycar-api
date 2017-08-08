package com.happycar.api.service;

import java.util.Date;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.FundLogDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.PayOrderDao;
import com.happycar.api.dao.SignupDao;
import com.happycar.api.dao.SignupPaymentDao;
import com.happycar.api.model.HcFundLog;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcPayOrder;
import com.happycar.api.model.HcSignup;
import com.happycar.api.model.HcSignupPayment;


@Service
public class SignupService {
	private Logger logger = Logger.getLogger(SignupService.class);
	@Autowired
	private PayOrderDao payOrderDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private FundLogDao fundLogDao;
	@Autowired
	private SignupPaymentDao signupPaymentDao;
	@Autowired
	private SignupDao signupDao;
	@Autowired
	private CommissionService commissionService;

	@Transactional
	public void pay(Integer signupPaymentId) {
		//学费支付
		HcSignupPayment signupPayment = signupPaymentDao.findOne(signupPaymentId);
		if(signupPayment==null){
			logger.error("SignupPayment can not found,id is "+signupPaymentId);
			throw new RuntimeException("SignupPayment can not found");
		}
		if(signupPayment.getStatus()!=0){
			throw new RuntimeException("SignupPayment status is not 0,id is "+signupPaymentId);
		}
		if(signupPayment.getIsDeleted()==1){
			throw new RuntimeException("SignupPayment is deleted,id is "+signupPaymentId);
		}
		HcSignup signup = signupDao.findOne(signupPayment.getSignupId());
		if(signup==null){
			logger.error("Signup can not found,signup id is "+signupPayment.getSignupId());
			throw new RuntimeException("SignupPayment can not found");
		}
		signup.setPayAmount(signup.getPayAmount()+signupPayment.getPayAmount());
		signup.setUpdateTime(new Date());
		if(signup.getAmount().floatValue()==signup.getPayAmount()){
			signup.setStatus(2);//全部付款
		}else{
			signup.setStatus(1);//部分付款
		}
		signupDao.save(signup);
		signupPayment.setStatus(1);
		signupPayment.setUpdateTime(new Date());
		signupPaymentDao.save(signupPayment);
		HcMember member = memberDao.findOne(signup.getMemberId());
		if(member.getProgress()<=2){
			//进度更新
			if(signup.getPayType()==2){
				member.setProgress(1);
			}else{
				member.setProgress(2);
			}
			//如果已完全支付,则进度更新为2
			if(signup.getAmount()==(signup.getPayAmount()+signup.getCouponAmount())){
				member.setProgress(2);
			}
		}
		member.setSignupId(signup.getId());
		member.setSignupDate(new Date());
		member.setDrivingLicenseType(signup.getDrivingLicenseType());
		member.setUpdateTime(new Date());
		memberDao.save(member);
		//分配佣金
		commissionService.allotBySignupPaymentId(signupPayment.getId());
	}

}
