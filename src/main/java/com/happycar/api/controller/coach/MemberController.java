package com.happycar.api.controller.coach;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcMemberVO;
import com.happycar.api.vo.HcSchoolVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "我的学员管理")
@RestController("coachMemberController")
@RequestMapping("/api/coach/member")	
@Authentication
public class MemberController extends BaseController{
	private Logger logger = Logger.getLogger(MemberController.class);
	@Resource
	private MemberDao memberDao;
	
	@ApiOperation(value = "我的学员列表", httpMethod = "GET", notes = "我的学员列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		List<HcMember> list = memberDao.findAllByCoachIdAndProgressLessThanAndIsDeleted(coach.getId(),12,0);	
		List<HcMemberVO> members = new ArrayList<HcMemberVO>();
		for (HcMember member : list) {
			HcMemberVO memberVO = new HcMemberVO();
			BeanUtil.copyProperties(member, memberVO);
			HcSchoolVO schoolVO = new HcSchoolVO();
			members.add(memberVO);
		}
		model.addAttribute("members", members);
		MessageUtil.success("获取成功", model);
		return model;
	}

	@ApiOperation(value = "学员详情", httpMethod = "GET", notes = "学员详情")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "教练id", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel detail(Integer id,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = memberDao.findOne(id);	
		HcMemberVO memberVO = new HcMemberVO();
		BeanUtil.copyProperties(member, memberVO);
		model.addAttribute("member", memberVO);
		MessageUtil.success("获取成功", model);
		return model;
	}
}
