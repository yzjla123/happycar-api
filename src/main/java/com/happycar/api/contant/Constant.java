package com.happycar.api.contant;

public class Constant {
	/************************************SYSTEM***************************************/
	//KEY
	public static final String KEY_LOGIN_PASSENGER="loginPassenger";
	public static final String KEY_LOGIN_COACH="loginCoach";
	public static final String KEY_ACCESS_TOKEN="accessToken";
	public static final String DATA_CACHE_TYPE="redis";//验证码缓存Redis的key
	public static final String DATA_CACHE_PREFIX="aircar_";//加密时候前缀
	public static final String REDIS_KEY_SMS_CODE="sms_code";
	public static final String REDIS_VERIFY_CODE="verify_code_";
	/************************************RESOURCE***************************************/
	public static final String RESOURCE_ROOT_WINDOW="D://ucar/api/resource";
	public static final String RESOURCE_ROOT_LINUX="/home/ucar/api/resource";
	public static final String RESOURCE_ROOT_MAC="/Users/zhengjianye/ucar/api/resource";
	/************************************PAY TYPE***************************************/
	public static final int PAY_TYPE_RECHARGE=1;//类型 1充值 2学费支付 3:推荐分成金
	public static final int PAY_TYPE_TUITION=2;
	/************************************ORDER STATUS***************************************/
	public static final int SELFDRIVE_ORDER_STATUS_WAIT=1;//状态 1 待开始 2 执行中 3 已取消(乘客取消) 4 待结算 5 待评价 6 已完成7 已终止(平台取消)
	public static final int SELFDRIVE_ORDER_STATUS_RUN=2;
	public static final int SELFDRIVE_ORDER_STATUS_CANCEL=3;
	public static final int SELFDRIVE_ORDER_STATUS_PAY=4;
	public static final int SELFDRIVE_ORDER_STATUS_COMMENT=5;
	public static final int SELFDRIVE_ORDER_STATUS_COMPLETE=6;
	public static final int SELFDRIVE_ORDER_STATUS_TERMIN=7;
	/************************************PARAM CODE***************************************/
	public static final String PARAM_CODE_SCHEDULE_DURATION = "SCHEDULE_DURATION";
	public static final String PARAM_CODE_ACTIVITY_REG_COUPON = "REGISTER_COUPON";
	public static final String PIC_COACH = "http://ookxrria2.bkt.clouddn.com/hc-coach-avatar.png";
	public static final String PIC_MEMBER = "http://ookxrria2.bkt.clouddn.com/hc-member-avatar.png";
	public static final String PARAM_CODE_BANNER = "BANNER";
	public static final String PARAM_CODE_COMMISSION = "COMMISSION";
	public static final String PARAM_CODE_SHARE = "SHARE";
	public static final String PARAM_CODE_SERVICE_PHONE = "SERVICE_PHONE";
	public static final String PARAM_CODE_ALI_PARTNER = "ALI_PARTNER";
	public static final String PARAM_CODE_ALI_APPID = "ALI_APPID";
	public static final String PARAM_CODE_ALI_SELLER = "ALI_SELLER";
	public static final String PARAM_CODE_ALI_PRI_KEY = "ALI_PRI_KEY";
	public static final String PARAM_CODE_ALI_PUB_KEY = "ALI_PUB_KEY";
	public static final String PARAM_CODE_ALI_BACK = "ALI_BACK";
	public static final String PARAM_CODE_WX_PARTNER = "WX_APPID";
	public static final String PARAM_CODE_WX_APPID = "WX_PARTNER";
	public static final String PARAM_CODE_WX_PARTNER_KEY = "WX_PARTNER_KEY";
	public static final String PARAM_CODE_WX_ORDER_URL = "WX_ORDER_URL";
	public static final String PARAM_CODE_WX_NOTIFY_URL = "WX_NOTIFY_URL";
	public static final String PARAM_CODE_SUBJECT_FEE = "SUBJECT_FEE_%s";
	
}
