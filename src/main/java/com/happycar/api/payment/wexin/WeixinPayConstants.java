package com.happycar.api.payment.wexin;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.SysParamDao;
import com.happycar.api.model.HcSysParam;
import com.happycar.api.utils.SpringContextUtil;

public class WeixinPayConstants {  
    
    public static String getAppid(){
    	SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_WX_APPID);
		return param.getExt1();
    }
    
    public static String getPartner(){
    	SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_WX_PARTNER);
		return param.getExt1();
    }
    
    public static String getPartnerKey(){
    	SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_WX_PARTNER_KEY);
		return param.getExt1();
    }
    
    public static String getOrderUrl(){
    	SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_WX_ORDER_URL);
		return param.getExt1();
    }
    
    public static String getNotifyUrl(){
    	SysParamDao paramDao = SpringContextUtil.getApplicationContext().getBean(SysParamDao.class);
		HcSysParam param = paramDao.findByCode(Constant.PARAM_CODE_WX_NOTIFY_URL);
		return param.getExt1();
    }
}  
