package com.happycar.api.controller.coach;

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
import com.happycar.api.service.ActivityService;
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


@Api(value = "教练管理")
@RestController("coachCoachController")
@RequestMapping("/api/coach")	
public class CoachController extends BaseController{
	
	private Logger logger = Logger.getLogger(CoachController.class);
	@Resource
	private MemberDao memberDao;
	@Resource
	private CoachDao coachDao;
	@Resource
	private ActivityService activityService;
	
	@ApiOperation(value = "保存用户头像", httpMethod = "POST", notes = "保存用户头像")
	@RequestMapping(value = "/pic", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "pic", value = "图片地址", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Authentication
	public ResponseModel savePic(String pic,
			 HttpServletRequest request,
			 HttpServletResponse response){
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		if(pic==null){
			MessageUtil.fail("图片地址不能为空!", model);
			return model;
		}
		coach.setPic(pic);
		coach.setUpdateTime(new Date());
		coachDao.save(coach);
		MessageUtil.success("上传成功!", model);
		return model;
	}
}
