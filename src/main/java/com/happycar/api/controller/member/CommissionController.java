package com.happycar.api.controller.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CommissionLogDao;
import com.happycar.api.dao.CouponDao;
import com.happycar.api.model.HcCommissionLog;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.ShareCodeUtil;
import com.happycar.api.vo.HcCommissionLogVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "佣金管理")
@RestController
@RequestMapping("/api/member/commission")	
@Authentication
public class CommissionController extends BaseController{
	
	private Logger logger = Logger.getLogger(CommissionController.class);
	@Resource
	private CommissionLogDao commissionLogDao;
	
	@ApiOperation(value = "用户佣金", httpMethod = "GET", notes = "获得用户佣金")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel getCommission(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		model.addAttribute("commission", member.getCommission());
		MessageUtil.success("获取成功", model);
		return model;
	}

}
