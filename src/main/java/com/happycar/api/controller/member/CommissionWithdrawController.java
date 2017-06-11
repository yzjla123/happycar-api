package com.happycar.api.controller.member;

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
import com.happycar.api.dao.CommissionWithdrawDao;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.model.HcCommissionWithdraw;
import com.happycar.api.model.HcMember;
import com.happycar.api.utils.BeanUtil;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.HcCommissionWithdrawVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "提现管理")
@RestController
@RequestMapping("/api/member/commissionWithdraw")	
@Authentication
public class CommissionWithdrawController extends BaseController{
	
	private Logger logger = Logger.getLogger(CommissionWithdrawController.class);
	@Resource
	private CommissionWithdrawDao commissionWithdrawDao;
	@Resource
	private MemberDao memberDao;
	
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
		HcMember member = getLoginMember(request);
		Page<HcCommissionWithdraw> page = commissionWithdrawDao.findByMemberIdOrderByIdDesc(member.getId(), pageable);
		List<HcCommissionWithdraw> list = page.getContent();
		List<HcCommissionWithdrawVO> list1 = new ArrayList<>();
		for (HcCommissionWithdraw commissionWithdraw : list) {
			HcCommissionWithdrawVO commissionWithdrawVO = new HcCommissionWithdrawVO();
			BeanUtil.copyProperties(commissionWithdraw, commissionWithdrawVO);
			list1.add(commissionWithdrawVO);
		}
		Page<HcCommissionWithdrawVO> page1 = new PageImpl<>(list1, pageable, page.getTotalElements());
		
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
			HcCommissionWithdraw withdraw,
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
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
		if(member.getCommission()<withdraw.getAmount()){
			MessageUtil.fail("余额不足", model);
			return model;
		}
		withdraw.setMemberId(member.getId());
		withdraw.setStatus(0);
		withdraw.setAddTime(new Date());
		withdraw.setUpdateTime(new Date());
		commissionWithdrawDao.save(withdraw);
		memberDao.reduceCommission(withdraw.getAmount(), member.getId());
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
	public ResponseModel add(
			Integer id,
			HttpServletRequest request,
			@ApiIgnore Pageable pageable){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		HcCommissionWithdraw withdraw = commissionWithdrawDao.findOne(id);
		if(withdraw==null){
			MessageUtil.fail("未找到提现记录", model);
			return model;
		}
		if(withdraw.getMemberId()!=member.getId().intValue()){
			MessageUtil.fail("没有权限", model);
			return model;
		}
		//取消提现
		withdraw.setStatus(1);
		withdraw.setUpdateTime(new Date());
		commissionWithdrawDao.save(withdraw);
		//返还佣金
		memberDao.addCommission(withdraw.getAmount(), member.getId());
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
		HcMember member = getLoginMember(request);
		HcCommissionWithdraw withdraw = commissionWithdrawDao.findByMemberIdOrderByIdDesc(member.getId());
		if(withdraw!=null){
			model.addAttribute("alipayName", withdraw.getAlipayName());
			model.addAttribute("alipayAccount", withdraw.getAlipayAccount());
		}
		MessageUtil.success("获取信息成功", model);
		return model;
	}
}
