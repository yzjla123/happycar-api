package com.happycar.api.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happycar.api.contant.ErrorCode;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResponseModel exceptionHandler(RuntimeException e, HttpServletResponse response) {
		logger.error("系统发生异常",e);
		ResponseModel model = new ResponseModel();
		MessageUtil.fail("发生异常,请稍候再试!", model);
		if (e instanceof BussinessException) {
			MessageUtil.fail(e.getMessage(),((BussinessException) e).getErorCode(), model);
		}
//		else{
//			resp.setRet(ErrorCode.UNDEFINE.getErrorCode());
//			resp.setError(ErrorCode.UNDEFINE.getMsg());
//		}
		return model;
	}
}