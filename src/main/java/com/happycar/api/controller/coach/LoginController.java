package com.happycar.api.controller.coach;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.TokenProcessor;
import com.happycar.api.vo.HcCoachVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "教练登录管理")
@RestController
@RequestMapping("/api/coach")	
public class LoginController extends BaseController{
	
	private Logger logger = Logger.getLogger(LoginController.class);
	@Resource
	private CoachDao coachDao;
	
	@ApiOperation(value = "登录系统", httpMethod = "GET", notes = "登录系统")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel login(
			HcCoach coach,
			String verifyCode,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		if(StringUtils.isEmpty(coach.getPhone())){
			MessageUtil.fail("手机号不能为空", model);
			return model;
		}
		if(StringUtils.isEmpty(verifyCode)){
			MessageUtil.fail("验证码不能为空", model);
			return model;
		}
		String verifyCode1 = RedisUtil.getString(Constant.REDIS_VERIFY_CODE+coach.getPhone());
		if(!verifyCode.equals(verifyCode1)){
			MessageUtil.fail("验证码不正确", model);
			return model;
		}
		List<HcCoach> list = coachDao.findByPhoneAndIsDeleted(coach.getPhone(),0);
		if(list.size()==0){
			MessageUtil.fail("手机号不存在", model);
			return model;
		}
		HcCoachVO coachVO = new HcCoachVO();
		BeanUtil.copyProperties(list.get(0),coachVO);
		String token = TokenProcessor.getInstance().generateToken(coachVO.getPhone(), true);
		RedisUtil.setString(Constant.KEY_ACCESS_TOKEN + token, coachVO.getId() + "",24*60*60);
		model.addAttribute("coach", coachVO);
		model.addAttribute("token", token);
		MessageUtil.success("操作成功", model);
		return model;
	}

	
}
