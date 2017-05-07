package com.happycar.api.controller;

import javax.servlet.http.HttpServletRequest;

import com.happycar.api.contant.Constant;
import com.happycar.api.model.HcCoach;
import com.happycar.api.model.HcMember;

public class BaseController {
	
	public HcMember getLoginMember(HttpServletRequest request){
		return (HcMember)request.getAttribute(Constant.KEY_LOGIN_PASSENGER);
	}
	
	public HcCoach getLoginCoach(HttpServletRequest request){
		return (HcCoach)request.getAttribute(Constant.KEY_LOGIN_COACH);
	}

}
