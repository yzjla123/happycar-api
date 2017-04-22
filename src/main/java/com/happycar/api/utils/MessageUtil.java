package com.happycar.api.utils;

import com.happycar.api.contant.ErrorCode;
import com.happycar.api.vo.ResponseModel;

public class MessageUtil {

	public static void success(String msg, ResponseModel model) {
		model.addAttribute("success",true);
		model.addAttribute("errorCode", 0);
		model.addAttribute("msg",msg);
	}

	public static void success(ResponseModel model) {
		model.addAttribute("success",true);
		model.addAttribute("errorCode", 0);
		model.addAttribute("msg",null);
	}
	
	public static void fail(String msg, ResponseModel model) {
		model.addAttribute("msg", msg);
		model.addAttribute("errorCode", 1);
		model.addAttribute("success", false);
	}	
	
	public static void fail(String msg,int errorCode, ResponseModel model) {
		model.addAttribute("msg", msg);
		model.addAttribute("errorCode", errorCode);
		model.addAttribute("success", false);
	}	
	
	public static void fail(ErrorCode errorCode, ResponseModel model) {
		model.addAttribute("msg", errorCode.getMsg());
		model.addAttribute("errorCode", errorCode.getErrorCode());
		model.addAttribute("success", false);
	}

}
