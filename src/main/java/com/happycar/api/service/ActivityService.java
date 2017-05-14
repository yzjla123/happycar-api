package com.happycar.api.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.ActivityLogDao;
import com.happycar.api.dao.CouponDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcActivityLog;
import com.happycar.api.model.HcCoupon;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.CodeGenerator;
import com.qiniu.util.StringUtils;

@Service
public class ActivityService {

	@Resource
	private ActivityLogDao activityLogDao;
	@Resource
	private SysParamDao paramDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private CouponDao couponDao;
	
	/**
	 * 注册送优惠券
	 */
	@Transactional
	public void registerCoupon(Integer memberId){
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ACTIVITY_REG_COUPON);
		if(param==null) return;
		if(StringUtils.isNullOrEmpty(param.getExt1())||"0".equals(param.getExt1())) return;
		HcMember member = memberDao.findOne(memberId);
		if(member==null) return;
		String name = param.getExt2();
		String type = param.getExt3();
		String amount = param.getExt4();
		String validDay = param.getExt5();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, Integer.parseInt(validDay));
		//添加优惠券
		HcCoupon coupon = new HcCoupon();
		coupon.setMemberId(memberId);
		coupon.setAmount(Float.parseFloat(amount));
		coupon.setMinMoney(0);
		coupon.setName(name);
		coupon.setNo(CodeGenerator.couponNo());
		coupon.setStatus(1);
		coupon.setValidDate(cal.getTime());
		coupon.setAddTime(new Date());
		coupon.setIsDeleted(0);
		coupon.setType(Integer.parseInt(type));
		coupon.setUpdateTime(new Date());
		couponDao.save(coupon);
		//添加日志记录
		HcActivityLog activityLog = new HcActivityLog();
		activityLog.setAmount(Float.parseFloat(amount));
		activityLog.setMemberId(memberId);
		activityLog.setName(name);
		activityLog.setAddTime(new Date());
		activityLogDao.save(activityLog);
	}
}
