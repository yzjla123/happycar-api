package com.happycar.api.controller.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtils;
import com.happycar.api.utils.LocationUtils;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCoachVO;
import com.happycar.api.vo.HcSchoolVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "司机管理")
@RestController
@RequestMapping("/api/member/coach")	
public class CoachController extends BaseController{
	
	private Logger logger = Logger.getLogger(CoachController.class);
	@Resource
	private CoachDao coachDao;
	
	@ApiOperation(value = "司机列表", httpMethod = "GET", notes = "司机列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(Double lon,Double lat,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		List<HcCoach> list = coachDao.findAllByIsDeleted(0);	
		List<HcCoachVO> coachs = new ArrayList<HcCoachVO>();
		for (HcCoach coach : list) {
			HcCoachVO coachVO = new HcCoachVO();
			BeanUtils.copyNotNullProperties(coachVO, coach);
			HcSchoolVO schoolVO = new HcSchoolVO();
			BeanUtils.copyNotNullProperties(schoolVO,coach.getSchool());
			coachVO.setSchool(schoolVO);
			//计算距离
			if(lon!=null&&lat!=null){
				if(schoolVO.getLat()!=null&&schoolVO.getLon()!=null){
					Double distance = LocationUtils.getDistance(lat, lon, schoolVO.getLat(), schoolVO.getLon());
					coachVO.setDistance(distance);
				}
			}
			coachs.add(coachVO);
		}
		model.addAttribute("coachs", coachs);
		MessageUtil.success("获取成功", model);
		return model;
	}

}
