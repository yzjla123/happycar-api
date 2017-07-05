package com.happycar.api.utils;

import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.happycar.api.dao.SmsLogDao;
import com.happycar.api.model.HcSmsLog;

public class SMSUtil {
	public static String uid = "sms0594";//用户名
	public static String verifyTemplate = "401895";//验证码模版
    public static String pwd = "2143de07e41cc0813d432004d26f7a51";// 序列号首次激活时自己设定
    public static Logger logger = Logger.getLogger(SMSUtil.class);
	
	/**
	 * 发送短信
	 * @param phone
	 * @param message
	 * @return
	 */
	public static boolean send(String phone, String message){
		String ret = "";
		try {
			message = URLEncoder.encode(message, "UTF-8");
		    String url = "http://api.sms.cn/sms/?ac=send&uid=%s&pwd=%s&template=%s&mobile=%s&content={\"code\":%s}";
			url = String.format(url, uid,pwd,verifyTemplate,phone,message);
			String responseString = HttpUtil.getInstance().doGetRequest(url);
			responseString = responseString.trim();
			if (StringUtil.isNotNull(responseString)) {
				ret = JSON.parseObject(responseString).getString("stat");
			}
	    	SmsLogDao smsLogDao = SpringContextUtil.getApplicationContext().getBean(SmsLogDao.class);
	    	HcSmsLog smsLog = new HcSmsLog();
	    	smsLog.setContent(message);
	    	smsLog.setPhone(phone);
	    	smsLog.setStatus(Integer.parseInt(ret));
	    	smsLog.setAddTime(new Date());
	    	smsLog.setUpdateTime(new Date());
	    	smsLogDao.save(smsLog);
	    	if("100".equals(ret)){
	    		return true;
	    	}
		} catch (Exception e) {
			logger.error("send SMS fail",e);
		}
        return false;
    }

	public static void main(String[] args) {
		boolean result;
			try {
				result = SMSUtil.send("15860007245","1234");
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
