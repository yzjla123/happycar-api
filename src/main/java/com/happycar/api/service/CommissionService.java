package com.happycar.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.CommissionLogDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.MemberRelationDao;
import com.happycar.api.dao.SignupDao;
import com.happycar.api.dao.SignupPaymentDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcMemberRelation;
import com.happycar.api.model.HcSignup;
import com.happycar.api.model.HcSignupPayment;
import com.happycar.api.model.HcSysParam;

@Service
public class CommissionService {
	
	private Logger logger = Logger.getLogger(MemberRelationService.class);
	@Resource
	private CommissionLogDao commissionLogDao;
	@Resource
	private SignupPaymentDao paymentDao;
	@Resource
	private SignupDao signupDao;
	@Resource
	private MemberRelationDao relationDao;
	@Resource
	private SysParamDao paramDao;
	@Resource
	private MemberDao memberDao;
	
	public void allotBySignupPaymentId(int signupPaymentId){
		HcSignupPayment payment = paymentDao.findOne(signupPaymentId);
		if(payment==null){
			logger.info("signupPayment is null,signupPaymentId:"+signupPaymentId);
			return;
		}
		HcSignup signup = signupDao.findOne(payment.getSignupId());
		if(payment==null){
			logger.info("signup is null,signupId:"+payment.getSignupId());
			return;
		}
		int memberId = signup.getMemberId();
		List<HcMemberRelation> relations = relationDao.findByCid(memberId);
		//加载各级分配策略
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_COMMISSION);
		if(param==null){
			logger.info("commission param is null,commission code:"+Constant.PARAM_CODE_COMMISSION);
			return;
		}
		//根据各级分配佣金
		for (HcMemberRelation relation : relations) {
			int pid = relation.getPid();
			float commission = 0;
			if(relation.getLevel()==1){
				commission = payment.getPayAmount()*Float.parseFloat(param.getExt1());
			}else if(relation.getLevel()==2){
				commission = payment.getPayAmount()*Float.parseFloat(param.getExt2());
			}else if(relation.getLevel()==3){
				commission = payment.getPayAmount()*Float.parseFloat(param.getExt3());
			}
//			memberDao.
		}
	}
}
