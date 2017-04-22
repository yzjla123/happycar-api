package com.happycar.api.payment.alipay;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Alipay {
	public String getSign(String subject, String body, String price, String orderNum) {
//		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(subject,body,price,orderNum,false);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//		System.out.println(orderParam);
		String privateKey = AlipayConfig.ali_private_key;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, false);
		final String orderInfo = orderParam + "&" + sign;
		return orderInfo;
	}
	
	public static void main(String[] args){
		String jsonString = "{\"appId\":\"2016082601807699\",\"authAppId\":\"2016082601807699\",\"body\":\"余额充值\",\"buyerId\":\"2088002014752173\",\"buyerLogonId\":\"mac***@sohu.com\",\"buyerPayAmount\":\"0.01\",\"charset\":\"utf-8\",\"fundBillList\":\"[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]\",\"gmtCreate\":\"2017-01-17 09:23:15\",\"gmtPayment\":\"2017-01-17 09:23:17\",\"invoiceAmount\":\"0.01\",\"notifyId\":\"f04acb12a848012dd5a379bf7870d5ahba\",\"notifyTime\":\"2017-01-17 09:23:17\",\"notifyType\":\"trade_status_sync\",\"outTradeNo\":\"201701170923041283498\",\"pointAmount\":\"0.00\",\"receiptAmount\":\"0.01\",\"sellerEmail\":\"zyxt@myriadautos.com\",\"sellerId\":\"2088421552797501\",\"sign\":\"rNjzIDTEzvS0vvqfSyq0bE7qGdgExCB2SCCB0Sv3u0MPGPJmO5yuLVHw4mdioaknRsQ665A2Ypwv6p4kIBZGnyzRo3ZqcL2Q8vRr8e1OfTcSCDFdB4UuItQdOdeCmIutilVhUyY81eQnE+PT7RKG+i0EIJbYMWOYpNGW014jknQ=\",\"signType\":\"RSA\",\"subject\":\"中迪用车\",\"totalAmount\":\"0.01\",\"tradeNo\":\"2017011721001004170255249517\",\"tradeStatus\":\"TRADE_SUCCESS\",\"version\":\"1.0\"}";
		AlipayNotification notice = JSON.parseObject(jsonString, AlipayNotification.class);
		//notice.setVerifyResult(verifyResult);
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(jsonString);
		System.out.println("----------------------------------------------------------------------------------");
		String resultResponse = "success";
		PrintWriter printWriter = null;
		try {
			
			//do business
			//if(verifyResult){
			//	accountOrderService.pay(notice.getOutTradeNo(), new BigDecimal(notice.getTotalFee()), "测试");
			//}
			//fail due to verification error
			//else{
				resultResponse = "fail";
			//}
			
		} catch (Exception e) {
//			logger.error("alipay notify error :", e);
			resultResponse = "fail";
			printWriter.close();
		}
		System.out.println(resultResponse);
		
		if (printWriter != null) {
			printWriter.print(resultResponse);
		}
	}

}
