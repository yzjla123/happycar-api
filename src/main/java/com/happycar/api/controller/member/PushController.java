package com.happycar.api.controller.member;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.PushInfoDao;
import com.happycar.api.model.HcPushInfo;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "推送管理")
@RestController
@RequestMapping("/api/push")	
public class PushController extends BaseController{
	
	private Logger logger = Logger.getLogger(PushController.class);
	@Resource
	private PushInfoDao pushInfoDao;
	
	@ApiOperation(value = "增加推送信息", httpMethod = "POST", notes = "增加推送信息")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "appId", value = "appId", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "appKey", value = "appKey", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "clientId", value = "clientId", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "phone", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型:member 成员,coach 教练", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(HcPushInfo pushInfo, HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		pushInfoDao.deleteByClientIdAndType(pushInfo.getClientId(),pushInfo.getType());
		pushInfo.setAddTime(new Date());
		pushInfo.setUpdateTime(new Date());
		pushInfoDao.save(pushInfo);
		MessageUtil.success("操作成功", model);
		return model;
	}
}
