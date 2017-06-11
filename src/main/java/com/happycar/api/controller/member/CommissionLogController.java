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
@RequestMapping("/api/member/commissionLog")	
@Authentication
public class CommissionLogController extends BaseController{
	
	private Logger logger = Logger.getLogger(CommissionLogController.class);
	@Resource
	private CommissionLogDao commissionLogDao;
	
	@ApiOperation(value = "佣金列表", httpMethod = "GET", notes = "佣金列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "第几页(0页开始)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel list(
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		Page<HcCommissionLog> page = commissionLogDao.findByPidAndStatusOrderByIdDesc(member.getId(),1,pageable);
		List<HcCommissionLog> list = page.getContent();
		List<HcCommissionLogVO> list1 = new ArrayList<>();
		for (HcCommissionLog commissionLog : list) {
			HcCommissionLogVO commissionLogVO = new HcCommissionLogVO();
			BeanUtil.copyProperties(commissionLog, commissionLogVO);
			list1.add(commissionLogVO);
		}
		Page<HcCommissionLogVO> page1 = new PageImpl<>(list1, pageable, page.getTotalElements());
		
		model.addAttribute("page", page1);
		MessageUtil.success("获取成功", model);
		return model;
	}

}
