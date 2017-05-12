package com.happycar.api.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.dao.ArticleDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcArticle;
import com.happycar.api.model.HcBook;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.service.QiniuService;
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "七牛管理")
@RestController
@RequestMapping("/api/qiniu")	
public class QiniuController extends BaseController{
	
	private Logger logger = Logger.getLogger(QiniuController.class);
	@Resource
	private QiniuService qiniuService;
	
	@ApiOperation(value = "获取uptoken", httpMethod = "GET", notes = "获取uptoken")
	@RequestMapping(value = "/uptoken", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Integer", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel uptoken(HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		String uptoken = qiniuService.getUploadToken();
		model.addAttribute("uptoken", uptoken);
		MessageUtil.success("操作成功", model);
		return model;
	}

}
