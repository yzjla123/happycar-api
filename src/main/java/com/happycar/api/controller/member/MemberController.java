package com.happycar.api.controller.member;

import java.util.Date;
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
import com.happycar.api.dao.MemberDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtils;
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


@Api(value = "学员管理")
@RestController
@RequestMapping("/api/member")	
public class MemberController extends BaseController{
	
	private Logger logger = Logger.getLogger(MemberController.class);
	@Resource
	private MemberDao memberDao;
	
	@ApiOperation(value = "注册学员", httpMethod = "GET", notes = "注册学员")
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "idcard", value = "身价证", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel reg(
			HcMember member,
			String verifyCode,
			HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		if(StringUtils.isEmpty(member.getPhone())){
			MessageUtil.fail("手机号不能为空", model);
			return model;
		}
		if(StringUtils.isEmpty(member.getName())){
			MessageUtil.fail("真实姓名不能为空", model);
			return model;
		}
		if(StringUtils.isEmpty(member.getIdcard())){
			MessageUtil.fail("身价证不能为空", model);
			return model;
		}
		if(StringUtils.isEmpty(verifyCode)){
			MessageUtil.fail("验证码不能为空", model);
			return model;
		}
		String verifyCode1 = RedisUtil.getString(Constant.REDIS_VERIFY_CODE+member.getPhone());
		if(!verifyCode.equals(verifyCode1)){
			MessageUtil.fail("验证码不正确", model);
			return model;
		}
		List<HcMember> list = memberDao.findByPhoneAndIsDeleted(member.getPhone(),0);
		if(list.size()>0){
			MessageUtil.fail("手机号已注册", model);
			return model;
		}
		member.setIsDeleted(0);
		member.setProgress(0);
		member.setAddTime(new Date());
		member.setUpdateTime(new Date());
		memberDao.save(member);
		MessageUtil.success("操作成功", model);
		return model;
	}
	
	
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
		if(!verifyCode.equals(verifyCode1)){
			MessageUtil.fail("验证码不正确", model);
			return model;
		}
		List<HcMember> list = memberDao.findByPhoneAndIsDeleted(member.getPhone(),0);
		if(list.size()==0){
			MessageUtil.fail("手机号不存在", model);
			return model;
		}
		HcMemberVO memberVO = new HcMemberVO();
		BeanUtils.copyNotNullProperties(memberVO, list.get(0));
		String token = TokenProcessor.getInstance().generateToken(member.getPhone(), true);
		RedisUtil.setString(Constant.KEY_ACCESS_TOKEN + token, member.getId() + "",24*60*60);
		model.addAttribute("member", memberVO);
		model.addAttribute("token", token);
		MessageUtil.success("操作成功", model);
		return model;
	}

}
