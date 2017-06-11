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
public class PayOrderService {
	private Logger logger = Logger.getLogger(PayOrderService.class);
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
	public boolean pay(String orderNo, String amount, String memo) {
		HcPayOrder payOrder = payOrderDao.findByOrderNoAndStatus(orderNo,0);
		if(payOrder==null){
			logger.error("Can`t found payOrder by orderNo,OrderNo is:"+orderNo);
			return false;
		}
		if(!payOrder.getAmount().equals(amount)){
			logger.error("Amount is not eqaul[payOrder amount:"+payOrder.getAmount()+"callback amount:"+amount+"]");
			return false;
		}
		payOrder.setStatus(1);
		payOrder.setOrderTime(new Date());
		HcMember member = memberDao.findOne(payOrder.getPid());
		member.setAmount(member.getAmount()+Float.parseFloat(amount));
		memberDao.save(member);
		HcFundLog fundLog = new HcFundLog();
		fundLog.setPid(member.getId());
		fundLog.setAmount(Float.parseFloat(amount));
		fundLog.setBalance(member.getAmount());
		fundLog.setRid(payOrder.getId());
		fundLog.setType(payOrder.getType());
		fundLog.setAddTime(new Date());
		fundLog.setRemark("余额充值");
		fundLogDao.save(fundLog);
		//学费支付
		if(Constant.PAY_TYPE_TUITION==payOrder.getType().intValue()){
			HcSignupPayment signupPayment = signupPaymentDao.findOne(payOrder.getRid());
			if(signupPayment==null){
				logger.error("SignupPayment can not found,id is "+payOrder.getRid());
				throw new RuntimeException("SignupPayment can not found");
			}
			if(signupPayment.getStatus()!=0){
				throw new RuntimeException("SignupPayment status is not 0,id is "+payOrder.getRid());
			}
			if(signupPayment.getIsDeleted()==1){
				throw new RuntimeException("SignupPayment is deleted,id is "+payOrder.getRid());
			}
			HcSignup signup = signupDao.findOne(signupPayment.getSignupId());
			if(signup==null){
				logger.error("Signup can not found,signup id is "+signupPayment.getSignupId());
				throw new RuntimeException("SignupPayment can not found");
			}
			signup.setPayAmount(signup.getPayAmount()+signupPayment.getPayAmount());
			signupDao.save(signup);
			signupPayment.setStatus(1);
			signupPaymentDao.save(signupPayment);
			if(member.getProgress()<=2){
				//进度更新
				if(signup.getPayAmount()==100){
					member.setProgress(1);
				}else{
					member.setProgress(2);
				}
			}
			member.setSignupId(signup.getId());
			member.setSignupDate(new Date());
			memberDao.save(member);
			//分配佣金
			commissionService.allotBySignupPaymentId(signupPayment.getId());
		}
		return true;
	}

}
