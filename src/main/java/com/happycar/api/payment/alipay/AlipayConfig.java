package com.happycar.api.payment.alipay;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.SpringContextUtil;

public class AlipayConfig {

	public static String getPartner(){
		SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ALI_PARTNER);
		return param.getExt1();
	}
	
	public static String getAppid(){
		SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ALI_APPID);
		return param.getExt1();
	}	

	public static String getSeller(){
		SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ALI_SELLER);
		return param.getExt1();
	}

	public static String getPrivateKey(){
		SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ALI_PRI_KEY);
		return param.getExt1();
	}
	
	public static String getPubKey(){
		SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ALI_PUB_KEY);
		return param.getExt1();
	}

	public static String getBackUrl(){
		SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_ALI_BACK);
		return param.getExt1();
	}
}