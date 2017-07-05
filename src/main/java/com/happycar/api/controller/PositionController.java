package com.happycar.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.dao.PositionDao;
import com.happycar.api.dao.PositionLogDao;
import com.happycar.api.model.HcPosition;
import com.happycar.api.model.HcPositionLog;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "位置管理")
@RestController
@RequestMapping("/api/position")	
public class PositionController extends BaseController{
	
	private Logger logger = Logger.getLogger(PositionController.class);
	@Resource
	private PositionDao positionDao;
	@Resource
	private PositionLogDao positionLogDao;
	
	@ApiOperation(value = "添加位置", httpMethod = "POST", notes = "添加位置")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(HcPosition position,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		if(position.getType()==null){
			MessageUtil.fail("类型不能为空", model);
			return model;
		}
		if(position.getRid()==null){
			MessageUtil.fail("rid不能为空", model);
			return model;
		}
		if(position.getLat()==null){
			MessageUtil.fail("纬度不能为空", model);
			return model;
		}
		if(position.getLng()==null){
			MessageUtil.fail("经度不能为空", model);
			return model;
		}
		position.setAddTime(new Date());
		position.setUpdateTime(new Date());
		//删除原来的位置
		positionDao.deleteByRidAndType(position.getRid(),position.getType());
		positionDao.save(position);
		HcPositionLog positionLog = new HcPositionLog();
		BeanUtil.copyProperties(position, positionLog);
		positionLogDao.save(positionLog);
		MessageUtil.success("保存成功", model);
		return model;
	}

}
