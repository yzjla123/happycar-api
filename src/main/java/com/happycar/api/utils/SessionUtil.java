package com.happycar.api.utils;

import javax.servlet.http.HttpServletRequest;

import com.happycar.api.contant.Constant;


public class SessionUtil {

	
	/**
	 * 获得登录会员id
	 * @param request
	 * @return
	 */
	public static Integer getPassengerId(HttpServletRequest request){
		String token = request.getParameter(Constant.KEY_ACCESS_TOKEN);
		return Integer.parseInt(RedisUtil.getString(Constant.DATA_CACHE_PREFIX+token));
	}
	
}
