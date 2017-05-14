package com.happycar.api.controller.member;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.StringUtil;
import com.happycar.api.utils.TokenProcessor;
import com.happycar.api.vo.HcMemberVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "学员登录管理")
@RestController
@RequestMapping("/api/member")	
public class LoginController extends BaseController{
	
	private Logger logger = Logger.getLogger(LoginController.class);
	@Resource
	private MemberDao memberDao;
	@Resource
	private CoachDao coachDao;
	
	
	
	@ApiOperation(value = "登录系统", httpMethod = "POST", notes = "登录系统")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel login(
			HcMember member,
			String verifyCode,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		if(StringUtils.isEmpty(member.getPhone())){
			MessageUtil.fail("手机号不能为空", model);
			return model;
		}
		if(StringUtils.isEmpty(verifyCode)){
			MessageUtil.fail("验证码不能为空", model);
			return model;
		}
		String verifyCode1 = RedisUtil.getString(Constant.REDIS_VERIFY_CODE+member.getPhone());
//		if(!verifyCode.equals(verifyCode1)){
//			MessageUtil.fail("验证码不正确", model);
//			return model;
//		}
		List<HcMember> list = memberDao.findByPhoneAndIsDeleted(member.getPhone(),0);
		if(list.size()==0){
			MessageUtil.fail("手机号不存在", model);
			return model;
		}
		HcMemberVO memberVO = new HcMemberVO();
		BeanUtil.copyProperties(list.get(0),memberVO);
		if(memberVO.getIdcard()!=null&&memberVO.getIdcard().length()==18)
			memberVO.setIdcard(memberVO.getIdcard().substring(0, 4)+"*********"+memberVO.getIdcard().substring(16, memberVO.getIdcard().length()));
		else{
			memberVO.setIdcard("");
		}
		String token = TokenProcessor.getInstance().generateToken(memberVO.getPhone(), true);
		RedisUtil.setString(Constant.KEY_ACCESS_TOKEN + token, memberVO.getId() + "",24*60*60);
		model.addAttribute("loginInfo", memberVO);
		model.addAttribute("token", token);
		MessageUtil.success("操作成功", model);
		return model;
	}

	
}
