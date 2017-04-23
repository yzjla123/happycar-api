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

import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.TheoryDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcTheory;
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


@Api(value = "理论管理")
@RestController
@RequestMapping("/api/member/theory")	
public class TheoryController extends BaseController{
	
	private Logger logger = Logger.getLogger(TheoryController.class);
	@Resource
	private TheoryDao theoryDao;
	
	@ApiOperation(value = "理论列表", httpMethod = "GET", notes = "理论列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectType", value = "科目类型", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "lastUpdateTime", value = "最后更新时间", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(Integer subjectType,Date lastUpdateTime,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		List<HcTheory> list = null;
		if(lastUpdateTime==null){
			list = theoryDao.findByIsDeleted(0);
		}else{
			list = theoryDao.findByUpdateTimeGreaterThan(lastUpdateTime);
		}
		model.addAttribute("theorys", list);
		MessageUtil.success("获取成功", model);
		return model;
	}

}
