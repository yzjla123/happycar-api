package com.happycar.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.contant.Constant;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.utils.RedisUtil;
import com.happycar.api.utils.StringUtil;
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
		System.out.println(verifyCode);
		RedisUtil.setString(Constant.REDIS_VERIFY_CODE + phone, verifyCode, 60);
		// boolean ret = SMSUtil.send(phone, "您好,您的验证码是:"+verifyCode);
		// if(ret){
		MessageUtil.success("发送成功!", model);
		// }else{
		// MessageUtil.fail("发送失败,稍候再试!", model);
		// }
		return model;
	}
}
