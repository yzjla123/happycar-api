package com.happycar.api.utils;

import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SMSUtil {
	public static String sn = "8SDK-EMY-6699-RIYRT";// 软件序列号,请通过亿美销售人员获取
    public static String key = "67ea345ee8ac2d285be15b2893044bbe";// 序列号首次激活时自己设定
    public static String password = "913412";// 密码,请通过亿美销售人员获取
    public static String baseUrl = "http://hprpt2.eucp.b2m.cn:8080/sdkproxy/";
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
			message = URLEncoder.encode("【中迪用车】"+message, "UTF-8");
		    String code = "";
		    long seqId = System.currentTimeMillis();
		    String param = "cdkey=" + sn + "&password=" + key + "&phone=" + phone + "&message=" + message + "&addserial=" + code + "&seqid=" + seqId;
		    String url = baseUrl + "sendtimesms.action";
			url = url + "?" + param;
			String responseString = HttpUtil.getInstance().doGetRequest(url);
			responseString = responseString.trim();
			if (null != responseString && !"".equals(responseString)) {
				ret = xmlMt(responseString);
			}
	    	
//	    	SmsLogDao smsLogDao = SpringContextUtil.getApplicationContext().getBean(SmsLogDao.class);
//	    	AirSmsLog smsLog = new AirSmsLog();
//	    	smsLog.setContent(message);
//	    	smsLog.setPhone(phone);
//	    	smsLog.setStatus(Integer.parseInt(ret));
//	    	smsLogDao.save(smsLog);
	    	if("0".equals(ret)){
	    		return true;
	    	}
		} catch (Exception e) {
			logger.error("send SMS fail",e);
		}
        return false;
    }
	
	/**
	 * 获取余额
	 * @return
	 */
	public static String getBalance() {
		String param = "cdkey=" + sn + "&password=" + key;
		String url = baseUrl + "querybalance.action";
		String ret = "失败";
		url = url + "?" + param;
		String responseString = HttpUtil.getInstance().doGetRequest(url);
		responseString = responseString.trim();
		if (null != responseString && !"".equals(responseString)) {
			ret = xmlResponse(responseString);
		}
		return ret;
	}

	// 统一解析格式
	public static String xmlResponse(String response) {
		String result = "失败";
		Document document = null;
		try {
			document = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		result = root.elementText("message");
		return result;
	}

	// 解析下发response
	public static String xmlMt(String response) {
		String result = "0";
		Document document = null;
		try {
			document = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			e.printStackTrace();
			result = "-250";
		}
		Element root = document.getRootElement();
		result = root.elementText("error");
		if (null == result || "".equals(result)) {
			result = "-250";
		}
		return result;
	}

	public static void main(String[] args) {
//		String url = "http://sdk4report1.eucp.b2m.cn:8080/sdkproxy/querybalance.action";
//		String param = "cdkey=6SDK-EKF-6687-KHQPL&password=795836";
//		String result;
//			try {
//				result = SMSUtil.sendSMS("18700781005","测试");
//				System.out.println(result);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
}
