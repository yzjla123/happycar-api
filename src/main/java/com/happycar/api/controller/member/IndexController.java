package com.happycar.api.controller.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.dao.CommentDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcComment;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.LocationUtils;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCommentVO;
import com.happycar.api.vo.HcBannerVO;
import com.happycar.api.vo.HcCoachVO;
import com.happycar.api.vo.HcSchoolVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "首页管理")
@RestController
@RequestMapping("/api/index")	
public class IndexController extends BaseController{
	
	private Logger logger = Logger.getLogger(IndexController.class);
	@Resource
	private SysParamDao paramDao;
	
//	@ApiOperation(value = "轮播图片列表", httpMethod = "GET", notes = "轮播图片列表")
//	@RequestMapping(value = "/banner", method = RequestMethod.GET)
//	@ApiImplicitParams(value = {
//			
//	})
//	@ApiResponses(value={
//			@ApiResponse(code = 200, message = "")
//	})
//	public ResponseModel banner(HttpServletRequest request){
//		ResponseModel model = new ResponseModel();
//		List<HcSysParam> list = paramDao.findByPCodeOrderBySeqAsc(Constant.PARAM_CODE_BANNER);
//		List<HcBannerVO> banners = new ArrayList<>(); 
//		for (HcSysParam hcSysParam : list) {
//			HcBannerVO banner = new HcBannerVO();
//			banner.setPic(hcSysParam.getValue());
//			banner.setHref(hcSysParam.getExt1());
//			banners.add(banner);
//		}
//		model.addAttribute("banners", banners);
//		MessageUtil.success("获取成功", model);
//		return model;
//	}
}
