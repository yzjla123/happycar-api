package com.happycar.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.happycar.api.contant.Constant;
import com.happycar.api.model.HcMember;

public class BaseController {
	
	public HcMember getLoginPassenger(HttpServletRequest request){
		return (HcMember)request.getAttribute(Constant.KEY_LOGIN_PASSENGER);
	}

}
