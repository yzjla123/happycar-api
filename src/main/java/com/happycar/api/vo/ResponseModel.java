package com.happycar.api.vo;

import java.util.HashMap;
import java.util.List;

public class ResponseModel extends HashMap<String, Object>{

	public void addAttribute(String key,Object value){
		this.put(key, value);
	}
}
