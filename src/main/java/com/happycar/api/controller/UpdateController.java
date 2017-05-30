package com.happycar.api.controller;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.dao.UpdateDao;
import com.happycar.api.model.HcUpdate;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcUpdateVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "更新管理")
@RestController
@RequestMapping("/api/update")	
public class UpdateController extends BaseController{
	
	private Logger logger = Logger.getLogger(UpdateController.class);
	@Resource
	private UpdateDao updateDao;
	
	@ApiOperation(value = "版本信息", httpMethod = "GET", notes = "获取最新版本信息")
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "类型 iOS,android", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "version", value = "版本号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "appid", value = "appid", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "imei", value = "imei", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel version(String type,String version,String appid,String imei,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcUpdate update = updateDao.findMaxVersionByType(type);
		HcUpdateVO updateVO = new HcUpdateVO();
		if(update==null){
			updateVO.setStatus(0);
			MessageUtil.success("操作成功", model);
			return model;
		}
		
		BeanUtil.copyProperties(update, updateVO);
		if(!update.getVersion().equals(version)){
			updateVO.setStatus(1);
		}else{
			updateVO.setStatus(0);
		}
		model.addAttribute("update", updateVO);
		MessageUtil.success("操作成功", model);
		return model;
	}

}
