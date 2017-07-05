package com.happycar.api.controller.coach;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachFundLogDao;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcCoachFundLog;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCoachFundLogVO;
import com.happycar.api.vo.HcCommissionLogVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "余额管理")
@RestController("coachAmountController")
@RequestMapping("/api/coach/balance")	
@Authentication
public class BalanceController extends BaseController{
	
	private Logger logger = Logger.getLogger(BalanceController.class);
	@Resource
	private CoachFundLogDao coachFundLogDao;
	
	@ApiOperation(value = "教练余额", httpMethod = "GET", notes = "获得教练余额")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel getAmount(HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		model.addAttribute("balance", coach.getBalance());
		MessageUtil.success("获取成功", model);
		return model;
	}
}
