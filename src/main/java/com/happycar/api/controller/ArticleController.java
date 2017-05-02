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
import com.happycar.api.utils.DateUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "文章管理")
@RestController
@RequestMapping("/api/article")	
public class ArticleController extends BaseController{
	
	private Logger logger = Logger.getLogger(ArticleController.class);
	@Resource
	private ArticleDao articleDao;
	@Resource
	private SysParamDao paramDao;
	
	@ApiOperation(value = "文章列表", httpMethod = "GET", notes = "文章列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "subjectCode", value = "文章分类代码", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(String subjectCode,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcSysParam param = paramDao.findByCode(subjectCode);
		List<HcArticle> articles = articleDao.findBySubjectIdOrderByUpdateTimeDesc(param.getId());
		model.addAttribute("articles", articles);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	
	@ApiOperation(value = "获取文章", httpMethod = "POST", notes = "通过id获取文章")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Integer", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel getById(Integer id,HttpServletRequest request) throws ParseException{
		ResponseModel model = new ResponseModel();
		HcArticle article = articleDao.findOne(id);
		model.addAttribute("article", article);
		MessageUtil.success("操作成功", model);
		return model;
	}

}
