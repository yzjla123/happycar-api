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
import com.happycar.api.dao.FeedbackDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.SignupDao;
import com.happycar.api.dao.SignupPaymentDao;
import com.happycar.api.dao.TuitionDao;
import com.happycar.api.model.HcCoupon;
import com.happycar.api.model.HcFeedback;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSignup;
import com.happycar.api.model.HcSignupPayment;
import com.happycar.api.model.HcTuition;
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


@Api(value = "反馈管理")
@RestController
@RequestMapping("/api/member/feedback")	
public class FeedBackController extends BaseController{
	
	private Logger logger = Logger.getLogger(FeedBackController.class);
	@Resource
	private FeedbackDao feedbackDao;

	
	@ApiOperation(value = "添加反馈", httpMethod = "POST", notes = "添加反馈")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "content", value = "内容", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	@Authentication
	public ResponseModel add(
			String content,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		if(StringUtils.isEmpty(content)){
			MessageUtil.fail("内容不能为空", model);
			return model;
		}
		HcFeedback feedback = new HcFeedback();
		feedback.setMemberId(member.getId());
		feedback.setContent(content);
		feedback.setAddTime(new Date());
		feedbackDao.save(feedback);
		MessageUtil.success("感谢您的反馈信息!", model);
		return model;
	}
	

}
