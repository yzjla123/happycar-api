package com.happycar.api.controller.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.dao.CommentDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcComment;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.LocationUtils;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCommentVO;
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
	@Resource
	private CommentDao commentDao;
	
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
			BeanUtil.copyProperties(coach, coachVO);
			HcSchoolVO schoolVO = new HcSchoolVO();
			BeanUtil.copyProperties(coach.getSchool(),schoolVO);
			coachVO.setSchool(schoolVO);
			//计算距离
			if(lon!=null&&lat!=null){
				if(schoolVO.getLat()!=null&&schoolVO.getLon()!=null){
					Double distance = LocationUtils.getDistance(lat, lon, schoolVO.getLat(), schoolVO.getLon());
					coachVO.setDistance(distance);
				}
			}
			coachVO.setDrivingLicenseType(coach.getDrivingLicenseType().getValue());
			coachs.add(coachVO);
		}
		model.addAttribute("coachs", coachs);
		MessageUtil.success("获取成功", model);
		return model;
	}
	

	@ApiOperation(value = "司机详情", httpMethod = "GET", notes = "司机详情")
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "教练id", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel detail(Integer id,Double lon,Double lat,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcCoach coach = coachDao.findOne(id);	
		HcCoachVO coachVO = new HcCoachVO();
		BeanUtil.copyProperties(coach, coachVO);
		HcSchoolVO schoolVO = new HcSchoolVO();
		BeanUtil.copyProperties(coach.getSchool(),schoolVO);
		coachVO.setSchool(schoolVO);
		//计算距离
		if(lon!=null&&lat!=null){
			if(schoolVO.getLat()!=null&&schoolVO.getLon()!=null){
				Double distance = LocationUtils.getDistance(lat, lon, schoolVO.getLat(), schoolVO.getLon());
				coachVO.setDistance(distance);
			}
		}
		coachVO.setDrivingLicenseType(coach.getDrivingLicenseType().getValue());
		List<HcComment> list = commentDao.findByCoachIdAndIsDeleted(id,0);
		List<HcCommentVO> commentVOs = new ArrayList<>();
		for (HcComment comment : list) {
			HcCommentVO commentVO = new HcCommentVO();
			BeanUtil.copyProperties(comment, commentVO);
			commentVOs.add(commentVO);
		}
		coachVO.setComments(commentVOs);
		
		model.addAttribute("coach", coachVO);
		MessageUtil.success("获取成功", model);
		return model;
	}

}
