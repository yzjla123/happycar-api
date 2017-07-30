package com.happycar.api.controller.member;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.contant.Constant;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.MemberHappyCoinLogDao;
import com.happycar.api.dao.ShareHappyCoinDao;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcMemberHappyCoinLog;
import com.happycar.api.model.HcShareHappyCoin;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.SpringContextUtil;
import com.happycar.api.vo.HcShareVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "分享管理")
@RestController
@RequestMapping("/api/member/share")
public class ShareController extends BaseController {
	private Logger logger = Logger.getLogger(ShareController.class);
	@Resource
	private ShareHappyCoinDao coinDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private MemberHappyCoinLogDao coinLogDao;
	
	@ApiOperation(value = "分享信息【我的】", httpMethod = "GET", notes = "获取分享信息【我的】")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	@Authentication
	public ResponseModel share(HttpServletRequest request, HttpServletResponse response) {
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		SysParamDao paramDao = (SysParamDao) SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_SHARE);
		HcShareVO shareVO = new HcShareVO();
		shareVO.setTitle(param.getExt1());
		shareVO.setContent(param.getExt2());
		shareVO.setHref(param.getExt3()+"?phone="+member.getPhone());
		shareVO.setPic(param.getExt4());
		model.addAttribute("share", shareVO);
		MessageUtil.success("操作成功!", model);
		return model;
	}
	
	@ApiOperation(value = "获得快乐币", httpMethod = "GET", notes = "获得快乐币")
	@RequestMapping(value = "/happyCoin", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "url", value = "分享地址", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "happyCoin", value = "快乐币", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dayTimes", value = "每天分享次数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	@Authentication
	@Transactional
	public ResponseModel happyCoin(
			String url,
			Integer happyCoin,
			Integer dayTimes,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		if(happyCoin<0){
			MessageUtil.fail("快乐币数量不能小于0", model);
			return model;
		}
		int times = coinDao.shareTime(url,member.getId());
		if(times<dayTimes){
			HcShareHappyCoin coin = new HcShareHappyCoin();
			coin.setHappyCoin(happyCoin);
			coin.setMemberId(member.getId());
			coin.setUrl(url);
			coin.setAddTime(new Date());
			coinDao.save(coin);
			memberDao.addHappyCoin(happyCoin, member.getId());
			HcMemberHappyCoinLog coinLog = new HcMemberHappyCoinLog();
			coinLog.setMemberId(member.getId());
			coinLog.setQuantity(happyCoin);
			coinLog.setRemark("分享赚取");
			coinLog.setType(1);
			coinLog.setTotal(member.getHappyCoin()+happyCoin);
			coinLog.setAddTime(new Date());
			coinLogDao.save(coinLog);
			MessageUtil.success("操作成功!", model);
			return model;
		}else{
			MessageUtil.fail("已超过次数!", model);
			return model;
		}
	}
}
