package com.happycar.api.controller.coach;

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
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.CoachWithdrawDao;
import com.happycar.api.dao.CoachDao;
import com.happycar.api.model.HcCoachWithdraw;
import com.happycar.api.model.HcCoach;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCoachWithdrawVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "提现管理")
@RestController("coachWithdraw")
@RequestMapping("/api/coach/withdraw")	
@Authentication
public class WithdrawController extends BaseController{
	
	private Logger logger = Logger.getLogger(WithdrawController.class);
	@Resource
	private CoachWithdrawDao coachWithdrawDao;
	@Resource
	private CoachDao coachDao;
	
	@ApiOperation(value = "提现列表", httpMethod = "GET", notes = "提现列表")
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
		HcCoach coach = getLoginCoach(request);
		Page<HcCoachWithdraw> page = coachWithdrawDao.findByCoachIdOrderByIdDesc(coach.getId(), pageable);
		List<HcCoachWithdraw> list = page.getContent();
		List<HcCoachWithdrawVO> list1 = new ArrayList<>();
		for (HcCoachWithdraw coachWithdraw : list) {
			HcCoachWithdrawVO coachWithdrawVO = new HcCoachWithdrawVO();
			BeanUtil.copyProperties(coachWithdraw, coachWithdrawVO);
			list1.add(coachWithdrawVO);
		}
		Page<HcCoachWithdrawVO> page1 = new PageImpl<>(list1, pageable, page.getTotalElements());
		
		model.addAttribute("page", page1);
		MessageUtil.success("获取成功", model);
		return model;
	}
	
	@ApiOperation(value = "添加提现", httpMethod = "POST", notes = "添加提现")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "amount", value = "提现金额(100元以上)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "alipayAccount", value = "支付宝账号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "alipayName", value = "支付宝名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel add(
			HcCoachWithdraw withdraw,
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		if(withdraw==null||withdraw.getAmount()<100){
			MessageUtil.fail("提现金额不能低于100元", model);
			return model;
		}
		if(withdraw.getAlipayAccount()==null){
			MessageUtil.fail("支付宝账户不能为空", model);
			return model;
		}
		if(withdraw.getAlipayName()==null){
			MessageUtil.fail("支付宝账户名称不能为空", model);
			return model;
		}
		if(coach.getBalance()<withdraw.getAmount()){
			MessageUtil.fail("余额不足", model);
			return model;
		}
		withdraw.setCoachId(coach.getId());
		withdraw.setStatus(0);
		withdraw.setAddTime(new Date());
		withdraw.setUpdateTime(new Date());
		coachWithdrawDao.save(withdraw);
		coachDao.reduceAmount(withdraw.getAmount(), coach.getId());
		MessageUtil.success("提交成功", model);
		return model;
	}
	
	@ApiOperation(value = "取消提现", httpMethod = "POST", notes = "取消提现")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "提现id", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel cancel(
			Integer id,
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		HcCoachWithdraw withdraw = coachWithdrawDao.findOne(id);
		if(withdraw==null){
			MessageUtil.fail("未找到提现记录", model);
			return model;
		}
		if(withdraw.getCoachId()!=coach.getId().intValue()){
			MessageUtil.fail("没有权限", model);
			return model;
		}
		//取消提现
		withdraw.setStatus(2);
		withdraw.setUpdateTime(new Date());
		coachWithdrawDao.save(withdraw);
		//返还提现金额
		coachDao.addAmount(withdraw.getAmount(), coach.getId());
		MessageUtil.success("提交成功", model);
		return model;
	}
	
	@ApiOperation(value = "支付宝帐户信息", httpMethod = "GET", notes = "获取支付宝帐户信息")
	@RequestMapping(value = "/alipayAccountInfo", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	public ResponseModel alipayAccountInfo(
			Integer id,
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcCoach coach = getLoginCoach(request);
		HcCoachWithdraw withdraw = coachWithdrawDao.findByCoachIdOrderByIdDesc(coach.getId());
		if(withdraw!=null){
			model.addAttribute("alipayName", withdraw.getAlipayName());
			model.addAttribute("alipayAccount", withdraw.getAlipayAccount());
		}
		MessageUtil.success("获取信息成功", model);
		return model;
	}
}
