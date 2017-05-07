package com.happycar.api.controller.member;

import java.text.ParseException;
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
import com.happycar.api.model.HcTheory;
import com.happycar.api.utils.MessageUtil;
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
			@ApiImplicitParam(name = "lastUpdateTime", value = "最后更新时间", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(Long lastUpdateTime,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		List<HcTheory> kemu1 = null;
		List<HcTheory> kemu4 = null;
//		if(lastUpdateTime==null){
			kemu1 = theoryDao.findBySubjectTypeAndIsDeletedOrderBySeqAsc(1, 0);
			kemu4 = theoryDao.findBySubjectTypeAndIsDeletedOrderBySeqAsc(4, 0);
//		}else{
//			kemu1 = theoryDao.findBySubjectTypeAndUpdateTimeGreaterThanAndIsDeletedOrderBySeqAsc(1,new Date(lastUpdateTime),0);
//			kemu4 = theoryDao.findBySubjectTypeAndUpdateTimeGreaterThanAndIsDeletedOrderBySeqAsc(4,new Date(lastUpdateTime),0);
//		}
		Date updateTime = theoryDao.findMaxUpdateTime();
		model.addAttribute("kemu1", kemu1);
		model.addAttribute("kemu4", kemu4);
		model.addAttribute("updateTime", updateTime.getTime());
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	@ApiOperation(value = "最后更新时间", httpMethod = "GET", notes = "最后更新时间")
	@RequestMapping(value = "/lastUpdateTime", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel lastUpdateTime(Long lastUpdateTime,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		Date updateTime = theoryDao.findMaxUpdateTime();
		model.addAttribute("updateTime", updateTime.getTime());
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	

}
