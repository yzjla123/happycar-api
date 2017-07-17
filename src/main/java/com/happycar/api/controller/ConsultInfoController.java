package com.happycar.api.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.dao.ConsultInfoDao;
import com.happycar.api.model.HcConsultInfo;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.StringUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "咨询管理")
@RestController
@RequestMapping("/api/consultInfo")	
public class ConsultInfoController extends BaseController{
	
	private Logger logger = Logger.getLogger(ConsultInfoController.class);
	@Resource
	private ConsultInfoDao consultInfoDao;
	
	@ApiOperation(value = "添加咨询", httpMethod = "POST", notes = "添加咨询")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(HcConsultInfo consultInfo,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		if(StringUtil.isNull(consultInfo.getName())){
			MessageUtil.fail("姓名不能为空", model);
			return model;
		}
		if(StringUtil.isNull(consultInfo.getPhone())){
			MessageUtil.fail("手机号不能为空", model);
			return model;
		}
		if(StringUtil.isNull(consultInfo.getAddress())){
			MessageUtil.fail("地址不能为空", model);
			return model;
		}
		consultInfo.setAddTime(new Date());
		consultInfo.setUpdateTime(new Date());
		consultInfo.setIsDeleted(0);
		consultInfo.setStatus(0);
		consultInfoDao.save(consultInfo);
		MessageUtil.success("提交成功,我们会尽快与你联系!", model);
		return model;
	}

}
