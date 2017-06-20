package com.happycar.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.SMSUtil;
import com.happycar.api.utils.SpringContextUtil;
import com.happycar.api.utils.StringUtil;
import com.happycar.api.vo.HcShareVO;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "工具管理")
@RestController
@RequestMapping("/api/util")
public class UtilController extends BaseController {
	private Logger logger = Logger.getLogger(UtilController.class);

	@ApiOperation(value = "发送验证码", httpMethod = "GET", notes = "发送验证码")
	@RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"), })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	public ResponseModel verifyCode(String phone, HttpServletRequest request, HttpServletResponse response) {
		ResponseModel model = new ResponseModel();
		String verifyCode = StringUtil.verifyCode();
		//有效期5分钟
		RedisUtil.setString(Constant.REDIS_VERIFY_CODE + phone, verifyCode, 60*5);
//		boolean ret = SMSUtil.send(phone,verifyCode);
		boolean ret = true;
		System.out.println(verifyCode);
		if (ret) {
			model.addAttribute("verifyCode", verifyCode);
			MessageUtil.success("发送成功!", model);
		} else {
			MessageUtil.fail("发送失败,稍候再试!", model);
		}
		return model;
	}
	
	@ApiOperation(value = "分享信息", httpMethod = "GET", notes = "获取分享信息")
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	public ResponseModel share(HttpServletRequest request, HttpServletResponse response) {
		ResponseModel model = new ResponseModel();
		SysParamDao paramDao = (SysParamDao) SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_SHARE);
		HcShareVO shareVO = new HcShareVO();
		shareVO.setTitle(param.getExt1());
		shareVO.setContent(param.getExt2());
		shareVO.setHref(param.getExt3());
		shareVO.setPic(param.getExt4());
		model.addAttribute("share", shareVO);
		MessageUtil.success("操作成功!", model);
		return model;
	}
	
	@ApiOperation(value = "服务电话", httpMethod = "GET", notes = "获取服务电话")
	@RequestMapping(value = "/servicePhone", method = RequestMethod.GET)
	@ApiImplicitParams(value = {
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	public ResponseModel servicePhone(HttpServletRequest request, HttpServletResponse response) {
		ResponseModel model = new ResponseModel();
		SysParamDao paramDao = (SysParamDao) SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_SERVICE_PHONE);
		model.addAttribute("phone", param.getExt1());
		MessageUtil.success("操作成功!", model);
		return model;
	}
}
