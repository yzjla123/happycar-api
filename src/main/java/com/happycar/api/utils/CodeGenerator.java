package com.happycar.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 代码生成器
 * @author zhengjy
 *
 */
public class CodeGenerator {
	
	/**
	 * 优惠券号
	 * @return
	 */
	public static String couponNo(){
		return "CP-"+new SimpleDateFormat("yyMMdd").format(new Date())+"-"+RandomUtil.generateOnlyNumber(6);
	}

}
