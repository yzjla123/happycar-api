package com.happycar.api.controller.member;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CouponDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.SignupDao;
import com.happycar.api.dao.TuitionDao;
import com.happycar.api.model.HcCoupon;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSignup;
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
	
	@ApiOperation(value = "报名", httpMethod = "GET", notes = "报名")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "tuitionId", value = "班级id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "memberId", value = "学员id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "refereePhone", value = "推荐人手机号", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "payType", value = "付款方式 0:全款,1:首付60%,2:预付款100元", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "0:未付款,1:已付款,2:已退款", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "payChannel", value = "支付渠道 0:支付宝,1:微信", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "couponId", value = "优惠券id", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel signup(
			HcSignup signup,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		if(signup.getTuitionId()==null){
			MessageUtil.fail("套餐不能为空", model);
			return model;
		}
		if(signup.getMemberId()==null){
			MessageUtil.fail("学员不能为空", model);
			return model;
		}
		if(signup.getPayType()==null){
			MessageUtil.fail("付款方式不能为空", model);
			return model;
		}
		if(signup.getPayChannel()==null){
			MessageUtil.fail("支付渠道不能为空", model);
			return model;
		}
		//学员验证
		HcMember member = memberDao.findOne(signup.getMemberId());
		if(member==null||member.getIsDeleted()==1){
			MessageUtil.fail("学员不存在", model);
			return model;
		}
		if(member.getProgress()>0){
			MessageUtil.fail("学员已报名", model);
			return model;
		}
		HcCoupon coupon = null;
		if(signup.getCouponId()!=null){
			coupon = couponDao.getOne(signup.getCouponId());
			if(coupon==null){
				MessageUtil.fail("未找到优惠券", model);
				return model;
			}
			if(coupon.getStatus()!=1||coupon.getIsDeleted()==1){
				MessageUtil.fail("优惠券无效", model);
				return model;
			}
			if(coupon.getMinMoney()>signup.getAmount()){
				MessageUtil.fail("优惠券满"+coupon.getMinMoney()+"才能使用", model);
				return model;
			}
			if(coupon.getMemberId().intValue()!=signup.getMemberId().intValue()){
				MessageUtil.fail("无权使用这张优惠券", model);
				return model;
			}
			if(coupon.getValidDate().getTime()<System.currentTimeMillis()){
				MessageUtil.fail("优惠券已过期", model);
				return model;
			}
			signup.setCouponAmount(coupon.getAmount());
		}
		float couponAmount = signup.getCouponAmount()==null?0:signup.getCouponAmount();
		float amount = signup.getAmount();
		float payAmount = amount - couponAmount;
		signup.setPayAmount(payAmount);
		signup.setIsDeleted(0);
		signup.setAddTime(new Date());
		signup.setUpdateTime(new Date());
		signupDao.save(signup);
		logger.info("添加报名信息:"+signup);
		//学员进度
		member.setProgress(0);
		member.setUpdateTime(new Date());
		memberDao.save(member);
		//优惠券
		if(coupon!=null){
			coupon.setStatus(2);
			couponDao.save(coupon);
		}
		MessageUtil.success("操作成功", model);
		return model;
	}
	

}
