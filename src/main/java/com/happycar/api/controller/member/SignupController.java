package com.happycar.api.controller.member;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CouponDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.MemberRelationDao;
import com.happycar.api.dao.SignupDao;
import com.happycar.api.dao.SignupPaymentDao;
import com.happycar.api.dao.TuitionDao;
import com.happycar.api.model.HcCoupon;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSignup;
import com.happycar.api.model.HcSignupPayment;
import com.happycar.api.model.HcTuition;
import com.happycar.api.service.MemberRelationService;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.TokenProcessor;
import com.happycar.api.vo.HcMemberVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "报名管理")
@RestController
@RequestMapping("/api/member/signup")	
public class SignupController extends BaseController{
	
	private Logger logger = Logger.getLogger(SignupController.class);
	@Resource
	private SignupDao signupDao;
	@Resource
	private TuitionDao tuitionDao;
	@Resource
	private CouponDao couponDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private SignupPaymentDao signupPaymentDao;
	@Resource
	private MemberRelationService relationService;
	
	@ApiOperation(value = "报名", httpMethod = "POST", notes = "报名")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "tuitionId", value = "班级id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "学员姓名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "idcard", value = "学员身份证", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "refereePhone", value = "推荐人手机号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "payType", value = "付款方式 0:全款,1:首付60%,2:预付款100元", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "payChannel", value = "支付渠道 0:支付宝,1:微信", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "couponId", value = "优惠券id", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	@Authentication
	public ResponseModel signup(
			Integer tuitionId,
			String name,
			String idcard,
			String refereePhone,
			Integer payType,
			Integer payChannel,
			Integer couponId,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		if(tuitionId==null||tuitionId==0){
			MessageUtil.fail("套餐不能为空", model);
			return model;
		}
//		if(StringUtils.isEmpty(name)){
//			MessageUtil.fail("学员姓名不能为空", model);
//			return model;
//		}
//		if(StringUtils.isEmpty(idcard)){
//			MessageUtil.fail("学员身份证不能为空", model);
//			return model;
//		}
		if(payType==null){
			MessageUtil.fail("付款方式不能为空", model);
			return model;
		}
		if(payChannel==null){
			MessageUtil.fail("支付渠道不能为空", model);
			return model;
		}
		//套餐
		HcTuition tuition = tuitionDao.findOne(tuitionId);
		if(tuition==null){
			MessageUtil.fail("套餐不能不存在", model);
			return model;
		}
		//学员验证
		member = memberDao.findOne(member.getId());
		if(member==null||member.getIsDeleted()==1){
			MessageUtil.fail("学员不存在", model);
			return model;
		}
		if(member.getProgress()>0){
			MessageUtil.fail("学员已报名", model);
			return model;
		}
		//初始化报名信息对象
		HcSignup signup = new HcSignup();
		signup.setTuitionId(tuitionId);
		signup.setMemberId(member.getId());
		signup.setRefereePhone(refereePhone);
		signup.setPayType(payType);
		signup.setCouponId(couponId);
		signup.setAmount(tuition.getAmount());
		HcCoupon coupon = null;
		if(couponId!=null&&couponId!=0){
			coupon = couponDao.getOne(couponId);
			if(coupon==null){
				MessageUtil.fail("未找到优惠券", model);
				return model;
			}
			if(coupon.getStatus()!=1||coupon.getIsDeleted()==1){
				MessageUtil.fail("优惠券无效", model);
				return model;
			}
			if(coupon.getMinMoney()>tuition.getAmount()){
				MessageUtil.fail("优惠券满"+coupon.getMinMoney()+"才能使用", model);
				return model;
			}
			if(coupon.getMemberId().intValue()!=member.getId().intValue()){
				MessageUtil.fail("无权使用这张优惠券", model);
				return model;
			}
			if(coupon.getValidDate().getTime()<System.currentTimeMillis()){
				MessageUtil.fail("优惠券已过期", model);
				return model;
			}
			signup.setCouponAmount(coupon.getAmount());
		}else{
			signup.setCouponId(0);
			signup.setCouponAmount(0f);
		}
		signup.setStatus(0);
		signup.setPayAmount(0f);
		signup.setIsDeleted(0);
		signup.setAddTime(new Date());
		signup.setUpdateTime(new Date());
		signupDao.save(signup);
		logger.info("添加报名信息:"+signup);
		//添加支付信息
		HcSignupPayment signupPayment = new HcSignupPayment();
		float couponAmount = signup.getCouponAmount()==null?0:signup.getCouponAmount();
		float payAmount = signup.getAmount();
		
		if(signup.getPayType()==1){
			//首付60%
			payAmount = (int) (payAmount*0.6);
		}else if(signup.getPayType()==2){
			//首付100元
			payAmount = 100;
		}
	    payAmount = Math.max(payAmount - couponAmount,0);
	    signupPayment.setPayAmount(payAmount);
	    signupPayment.setPayChannel(payChannel);
	    signupPayment.setSignupId(signup.getId());
	    signupPayment.setStatus(0);
	    signupPayment.setIsDeleted(0);
	    signupPayment.setAddTime(new Date());
	    signupPaymentDao.save(signupPayment);
		//学员信息
		if(signup.getPayAmount()==100){
			member.setProgress(1);
		}else{
			member.setProgress(2);
		}
		member.setSignupId(signup.getId());
		member.setSignupDate(new Date());
		memberDao.save(member);
		
//		member.setName(member.getName());
//		member.setIdcard(member.getIdcard());
//		member.setProgress(0);
//		member.setUpdateTime(new Date());
//		memberDao.save(member);
		//优惠券
		if(coupon!=null){
			coupon.setStatus(2);
			couponDao.save(coupon);
		}
		//生成学员关系表
		if(StringUtils.isNotEmpty(refereePhone)){
			relationService.add(member.getId(), refereePhone);
		}
		model.addAttribute("signupId", signup.getId());
		MessageUtil.success("操作成功", model);
		return model;
	}
	

}
