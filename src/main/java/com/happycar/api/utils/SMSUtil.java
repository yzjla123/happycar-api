package com.happycar.api.utils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.happycar.api.dao.SmsLogDao;
import com.happycar.api.model.HcSmsLog;

public class SMSUtil {
	public static Logger logger = Logger.getLogger(SMSUtil.class);
	public static String uid = "sms0594";//用户名
    public static String pwd = "2143de07e41cc0813d432004d26f7a51";// 序列号首次激活时自己设定
    
    public static String TPL_VERIFY = "401895";//验证码模版
    public static String TPL_EXAM_REJECT = "405790";//考试申请驳回
    public static String TPL_EXAMP_PASS = "405791";//考试已批准
	
	/**
	 * 发送短信
	 * @param phone
	 * @param message
	 * @return
	 */
	public static boolean send(String phone, String templateId,Map<String,String> paramMap){
		String ret = "";
		try {
		    String url = "http://api.sms.cn/sms/?ac=send&uid=%s&pwd=%s&template=%s&mobile=%s&content=%s";
			url = String.format(url, uid,pwd,templateId,phone,URLEncoder.encode(JSON.toJSONString(paramMap)), "UTF-8");
			System.out.println(url);
			String responseString = HttpUtil.getInstance().doGetRequest(url);
			responseString = responseString.trim();
			if (StringUtils.isNotEmpty(responseString)) {
				ret = JSON.parseObject(responseString).getString("stat");
			}
	    	SmsLogDao smsLogDao = SpringContextUtil.getApplicationContext().getBean(SmsLogDao.class);
	    	HcSmsLog smsLog = new HcSmsLog();
	    	smsLog.setContent(url);
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
		Map<String,String> map = new HashMap<String, String>();
		map.put("code", "1111");
		boolean result;
			try {
				result = SMSUtil.send("15860007245",TPL_VERIFY,map);
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
