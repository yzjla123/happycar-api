package com.happycar.api.utils;

import java.util.HashMap;
import java.util.Map;

import com.happycar.api.contant.ErrorCode;



public class ResponseUtil {
	
	public static Map<String,Object> success(Map<String,Object> map) {
		map.put("ret", 0);
		return map;
	}
	
	public static Map<String,Object> success(String msg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ret", 0);
		return map;
	}
	
	public static Map<String,Object> fail(ErrorCode errorCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ret", errorCode.getErrorCode());
		map.put("msg", errorCode.getMsg());
		return map;
	}
	
	public static Map<String,Object> fail(String msg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ret", 1);
		map.put("msg", msg);
		return map;
	}
}
