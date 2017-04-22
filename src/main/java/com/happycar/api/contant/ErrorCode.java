package com.happycar.api.contant;

public enum ErrorCode {
	/************************COMMON***********************************/
	SUCCESS(0,"操作成功"),
	DISABLED(-1,"账号已停用或已删除"),
	TOKEN_INVALID(1001,"登录已过期,请重新登录"),
	AUTH_FAILED(1002,"权限不足"),
	INFO_NOT_FOUND(1003,"未找到相关信息"),
	SYS_ERROE(1006,"系统异常，请稍后重试"),
	INFO_NOT_SELFDRIVE_AUDIT(1004,"您还未通过证件审核"),
	INFO_NOT_SELFDRIVE_BAND(1005,"您还未交担保金或担保金不足"),
	BUSSINESS_EXCEPTION(1098,"业务异常"),
	UNDEFINE(1099,"未定义错误"),
	/************************Passenger***********************************/
	LOGIN_ERROR_NAME_NOT_EMPTY(2101,"帐号不能为空"),
	LOGIN_ERROR_PASSWORD_NOT_EMPTY(2102,"密码不能为空"),
	LOGIN_ERROR_PASSWORD_INVALID(2103,"密码由数字和字母组成，并且要同时含有数字和字母，且长度要在6-16位之间"),
	LOGIN_ERROR_ACCOUNT_NOT_EXSIT(2104,"帐号不存在"),
	LOGIN_ERROR_PASSWORD_WRONG(2105,"帐号或密码不对"),
	LOGIN_ERROR_ACCOUNT_DISABLED(2106,"账号已停用"),
	PASSENGER_ERROR_USER_NOT_EXSIT(2111,"会员不存在"),
	PASSENGER_ERROR_ID_NOT_EXSIT(2112,"会员ID不能为空"),
	PASSENGER_ERROR_LACK_PHOTOS(2113,"缺少图片，请确认上传完毕后再进行提交"),
	PASSENGER_ERROR_PHONE_NOT_EMPTY(2114,"手机号不能为空"),
	PASSENGER_ERROR_PHONE_CODE_NOT_EMPTY(2115,"验证码不能为空"),
	PASSENGER_ERROR_PHONE_CODE_WRONG(2116,"验证码不正确"),
	PASSENGER_ERROR_PASSWORD_NOT_EMPTY(2117,"密码不能为空"),
	PASSENGER_ERROR_USER_EXSIT(2118,"会员已存在，请直接登陆"),
	PASSENGER_ERROR_CODE_EXPIRED(2119,"验证码已过期,请重新获取"),
	PASSENGER_ERROR_IDCARD_REGISTERED(2120,"该身份证号码已被注册，请不要重复注册"),
	OPERATION_PWD_NOT_NULL(2121,"开门密码不能为空"),
	OPERATION_PWD_HAS_BEEN_SET(2122,"已经设置过开门密码，如忘记请进行找回"),
	OPERATION_PWD_CANNOT_LOGIN_PWD(2123,"开门密码不能与登陆密码相同"),
	OPERATION_PWD_IDCARD_NUMBER_NOT_NULL(2124,"身份证号码不能为空"),
	OPERATION_PWD_IDCARD_NUMBER_WRONG(2125,"请输入正确的身份证号码"),
	OPERATION_PWD_WRONG(2126,"操作密码错误"),
	BIND_COMPANY_ID_NOT_NULL(2127,"公司id不能为空"),
	BIND_COMPANY_COMPANY_NOT_EXSIT(2128,"公司不存在"),
	BIND_COMPANY_HAVE_BINDING_COMPANY(2129,"您已绑定公司，请勿重复绑定"),
	BIND_COMPANY_HAVE_APPLY_COMPANY(2130,"您已申请绑定公司，已在审核状态中，请勿重复申请"),
	BIND_COMPANY_NOT_APPLY_BIND(2131,"您当前没有申请绑定公司"),
	BIND_COMPANY_NOT_BIND(2132,"您当前没有绑定公司"),
	
	/************************Account***********************************/
	ACCOUNT_ERROR_ID_NOT_EXSIT(2212,"会员ID不能为空"),
	
	//CAR
	CAR_OPEN_DOOR_FAIL(3101,"打开车门失败！"),
	//ADVERTISEMENT
	ADVERTISEMENT_AD_ID_NOT_EMPTY(4101,"广告ID不能为空"),
	ADVERTISEMENT_NOT_EXSIT(4101,"广告不存在"),
	/************************Selfdrive***********************************/
	//login
	PARKING_ERROR_ID_NOT_EMPTY(6101,"请选择服务网点"),
	SELFDRIVE_ERROR_APPLY_ID_NOT_EMPTY(6102,"未开始用车或未获得该车使用权"),
	SELFDRIVE_ERROR_FIND_CAR(6103,"找车失败，请稍后重试"),
	SELFDRIVE_ERROR_OPEN_CAR(6104,"开车门失败，请稍后重试"),
	SELFDRIVE_ERROR_CLOSE_CAR(6105,"关车门失败，请稍后重试"),
	SELFDRIVE_ERROR_CONTROL_SHUTDOWN_CAR(6106,"关车门失败，请确认车辆已熄火、"),
	SELFDRIVE_ERROR_CONTROL_CLOSE_CAR(6117,"关车门失败，请确认车辆已关好车门"),
	SELFDRIVE_ERROR_NO_ACCSECC(6107,"未获得该车使用权"),
	SELFDRIVE_ERROR_CANCEL_CAR_STATUS(6108,"只能取消待开始的订单"), 
	SELFDRIVE_ERROR_CANCEL_CAR(6109,"取消失败，请稍后重试"),
	SELFDRIVE_ERROR_RETURN_CAR(6110,"还车失败，请稍后重试"),
	SELFDRIVE_ERROR_RETURN_CAR_GPS(6111,"还车失败，请确认您已将车停在指定的停车地点"),
	SELFDRIVE_ERROR_RETURN_CAR_CLOSE(6112,"还车失败，请确认车门已关好"),
	SELFDRIVE_ERROR_RETURN_CAR_SHUTDOWN(6113,"还车失败，请确认车辆已熄火"),
	SELFDRIVE_ERROR_NO_ENOUGH_MONEY(6114,"您的当前账户余额不足，请充值以后再使用，谢谢"),
	SELFDRIVE_ERROR_HAVE_ORDER(6115,"您已经有一个使用中的订单，请您用车完成后再继续下单"),
	SELFDRIVE_ERROR_ERROR_ORDER(6116,"网络异常，下单失败，请稍后重试"),
	SELFDRIVE_ERROR_RETURN_CAR_STATUS(6117,"您的车辆正在维修中，请与客服联系确认"), 
	/************************Util***********************************/
	//phoneCheckCode
	PHONECHECKCODE_FREQUENCY(4103,"获取验证码太过频繁，请稍后再试"),
	PHONECHECKCODE_NOT_EMPTY(4104,"手机号码不能为空"),
	PHONECHECKCODE_FORMAT_WRONGT(4105,"手机号码格式不正确"),
	PHONECHECKCODE_EXSIT(4106,"手机号码已被注册"),
	PHONECHECKCODE_NO_CODE(4107,"手机号码今天获取得验证码已上限，请明天再试"),
	PHONECHECKCODE_NOT_EXSIT(4108,"系统中不存在该手机号码"),
	PROPOSAL_CONTENT_NOT_EMPTY(4109,"内容不能为空"),
	//Consignee
	CONSIGNEE_DEFAULT_NOT_EXISTS(4110,"收件信息不存在"),
	CONSIGNEE_EXCEEDING (4111,"越权操作"),
	//ACTIVITY_VOUCHER
	ACTIVITYVOUCHER_INVALID(4112,"兑换码无效"),
	ACTIVITYVOUCHER_EXCHANGED(4113,"优惠卷已兑换"),
	ACTIVITYVOUCHER_EFFECTIVE(4114,"请输入有效的优惠卷"),
	ACTIVITYVOUCHER_UNACTIVITY(4115,"优惠卷未激活"),
	ACTIVITYVOUCHER_OUT_EXCHANGED(4116,"优惠卷已过期"),
	ACTIVITYVOUCHER_ID_EMPTY(4117,"优惠卷ID不能为空"),
	ACTIVITYVOUCHER_APPLYNUMBER_EXCHANGED(4118,"申请单ID不能为空"),
	/************************PushBind***********************************/
	PUSHBIND_USERID_EMPTY(4119,"用户ID不能为空"),
	PUSHBIND_CHANELID_EMPTY(4120,"chanel_id不能为空"),
	PUSHBIND_DEVICETYPE_EMPTY(4121,"设备类型不能为空"),
	PUSHBIND_DEVICETYPE_ERROR(4122,"设备类型必须为1或2"),
	//validCheckCode
	VALIDCHENKCODE_PHONE_NOT_EMPTY(5201,"手机号不能为空"),
	VALIDCHENKCODE_PHONE_CODE_EXPIRED(5202,"验证码已过期"),
	VALIDCHENKCODE_PHONE_CODE_WRONG(5203,"验证码不正确"),
	
	;
	
	private int errorCode;
	private String msg;
	ErrorCode(int errorCode,String msg){
		this.errorCode = errorCode;
		this.msg = msg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public String getMsg() {
		return msg;
	}

}
