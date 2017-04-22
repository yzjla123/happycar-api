package com.happycar.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.dao.TuitionDao;
import com.happycar.api.model.HcCoupon;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcTuition;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCouponVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "报名套餐管理")
@RestController
@RequestMapping("/api/tuition")	
public class TuitionController extends BaseController{
	
	private Logger logger = Logger.getLogger(TuitionController.class);
	@Resource
	private TuitionDao tuitionDao;
	
	@ApiOperation(value = "获得套餐", httpMethod = "GET", notes = "获得套餐")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		List<HcTuition> list = tuitionDao.findAll();
		if(list.size()>0){
			HcTuition tuition = list.get(0);
			model.addAttribute("tuition", tuition);
			MessageUtil.success("获取成功", model);
		}else{
			MessageUtil.fail("没有套餐数据", model);
		}
		return model;
	}

}
