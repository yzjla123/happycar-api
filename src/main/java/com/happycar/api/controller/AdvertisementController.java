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
import com.happycar.api.dao.AdvertisementDao;
import com.happycar.api.dao.ArticleDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcAdvertisement;
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


@Api(value = "广告管理")
@RestController
@RequestMapping("/api/advertisement")	
public class AdvertisementController extends BaseController{
	
	private Logger logger = Logger.getLogger(AdvertisementController.class);
	@Resource
	private AdvertisementDao advertisementDao;
	
	@ApiOperation(value = "首页轮播图列表", httpMethod = "GET", notes = "首页轮播图列表")
	@RequestMapping(value = "/banners", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "city", value = "所属城市", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel banners(String city,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		List<HcAdvertisement> banners = advertisementDao.findByTypeAndIsDeleted(0,0);
		model.addAttribute("banners", banners);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	
	@ApiOperation(value = "消息中心广告", httpMethod = "GET", notes = "消息中心广告")
	@RequestMapping(value = "/messageCenterAd", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "city", value = "所属城市", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel messageCenterAd(String city,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		List<HcAdvertisement> messageCenterAds = advertisementDao.findByTypeAndIsDeleted(1,0);
		if(messageCenterAds.size()>0){
			model.addAttribute("messageCenterAd", messageCenterAds.get(0));
			MessageUtil.success("获取成功", model);
		}else{
			MessageUtil.fail("暂无广告信息", model);
		}
		return model;
	}
	
	
	@ApiOperation(value = "首页弹出广告", httpMethod = "GET", notes = "首页弹出广告")
	@RequestMapping(value = "/indexPopAd", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "city", value = "所属城市", required = false, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel indexPopAd(String city,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		List<HcAdvertisement> messageCenterAds = advertisementDao.findByTypeAndIsDeleted(2,0);
		if(messageCenterAds.size()>0){
			model.addAttribute("popAd", messageCenterAds.get(0));
			MessageUtil.success("获取成功", model);
		}else{
			MessageUtil.fail("暂无广告信息", model);
		}
		return model;
	}
}
