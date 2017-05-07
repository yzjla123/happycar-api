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

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CouponDao;
import com.happycar.api.model.HcCoupon;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCouponVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "优惠券管理")
@RestController
@RequestMapping("/api/member/coupon")	
@Authentication
public class CouponController extends BaseController{
	
	private Logger logger = Logger.getLogger(CouponController.class);
	@Resource
	private CouponDao couponDao;
	
	@ApiOperation(value = "优惠券列表", httpMethod = "GET", notes = "优惠券列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "类型 0:学费券", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(Integer type,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		List<HcCoupon> list = null;
		if(type!=null){
			list = couponDao.findByMemberIdAndStatusAndTypeAndValidDateGreaterThanAndIsDeletedOrderByValidDateAsc(member.getId(), 1,type,new Date(), 0);
		}else{
			list = couponDao.findByMemberIdAndStatusAndValidDateGreaterThanAndIsDeletedOrderByValidDateAsc(member.getId(), 1,new Date(), 0);	
		}
		List<HcCouponVO> coupons = new ArrayList<HcCouponVO>();
		for (HcCoupon coupon : list) {
			HcCouponVO couponVO = new HcCouponVO();
			BeanUtil.copyProperties(coupon,couponVO);
			coupons.add(couponVO);
		}
		model.addAttribute("coupons", coupons);
		MessageUtil.success("获取成功", model);
		return model;
	}

}
