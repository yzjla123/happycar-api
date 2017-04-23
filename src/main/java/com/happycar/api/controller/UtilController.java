package com.happycar.api.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;


@Api(value = "工具管理")
@RestController
@RequestMapping("/api/util")	
public class UtilController extends BaseController{
	private Logger logger = Logger.getLogger(UtilController.class);
	
}
