package com.happycar.api.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.CommissionLogDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.MemberRelationDao;
import com.happycar.api.dao.SignupDao;
import com.happycar.api.dao.SignupPaymentDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcCommissionLog;
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
	
	@Transactional
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
			float commission = 0;
			float ratio = 0;
			//每一级的分成
			if(relation.getLevel()==1){
				ratio = Float.parseFloat(param.getExt1());
				commission = payment.getPayAmount()*Float.parseFloat(param.getExt1());
			}else if(relation.getLevel()==2){
				ratio = Float.parseFloat(param.getExt2());
				commission = payment.getPayAmount()*Float.parseFloat(param.getExt2());
			}else if(relation.getLevel()==3){
				ratio = Float.parseFloat(param.getExt3());
				commission = payment.getPayAmount()*Float.parseFloat(param.getExt3());
			}
			HcCommissionLog commissionLog = new HcCommissionLog();
			commissionLog.setCid(relation.getCid());
			commissionLog.setPid(relation.getPid());
			commissionLog.setAmount(commission);
			commissionLog.setLevel(relation.getLevel());
			commissionLog.setRatio(ratio);
			commissionLog.setTotalAmount(payment.getPayAmount());
			commissionLog.setType(1);
			commissionLog.setStatus(0);
			commissionLogDao.save(commissionLog);
		}
	}
	
	/**
	 * 佣金结算,7天后佣金自动结算.
	 */
	public void commissionBattle(){
//		memberDao.addCommission(commission,relation.getPid());
//		logger.info("用户ID:"+relation.getPid()+"获得了"+commission+"佣金");
	}
}
