package com.happycar.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.payment.alipay.AlipayNotify;
import com.happycar.api.service.PayOrderService;
import com.happycar.api.utils.ObjectMapperUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/alipay")
@ApiIgnore
public class AlipayController extends BaseController {
	private Logger logger = Logger.getLogger(AlipayController.class);
	@Resource
	private PayOrderService payOrderService;
	/**
     * 支付回调页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notify(@ApiIgnore HttpServletRequest request,@ApiIgnore HttpServletResponse response) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		StringBuffer paramStr = new StringBuffer();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
			paramStr.append(name+"="+valueStr+"&");
		}
		logger.info("alipay notify,params:"+paramStr.toString());

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

		// 异步通知ID
		String notify_id = request.getParameter("notify_id");
		String totalAmount = request.getParameter("total_amount");

		// sign
		String sign = request.getParameter("sign");

		if (notify_id != null && !"".equals(notify_id)) {
			if (AlipayNotify.verifyResponse(notify_id).equals("true"))// 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
			{
				if (AlipayNotify.getSignVeryfy(params, sign))// 使用支付宝公钥验签
				{
					if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
						// 业务处理
						boolean result = payOrderService.pay(out_trade_no, totalAmount,ObjectMapperUtil.toString(params));
						if(result)
							return "success";
						else
							return "account fail";
					}else
						return "trade fail";
					
				} else// 验证签名失败
				{
					return "sign fail";
				}
			} else// 验证是否来自支付宝的通知失败
			{
				return "response fail";
			}
		} else {
			return "no notify message";
		}
		
//		Map<String, String> underScoreKeyMap = RequestUtils.getStringParams(request);
//		Map<String, String> camelCaseKeyMap = RequestUtils.convertKeyToCamelCase(underScoreKeyMap);
//		
//		//首先验证调用是否来自支付宝
//		boolean verifyResult = AlipayNotify.verify(underScoreKeyMap);
//		System.out.println("verifyResult:"+verifyResult);
//		try {
//			
//			String jsonString = JSON.toJSONString(camelCaseKeyMap);
//			//String jsonString = "{\"appId\":\"2016082601807699\",\"authAppId\":\"2016082601807699\",\"body\":\"余额充值\",\"buyerId\":\"2088002014752173\",\"buyerLogonId\":\"mac***@sohu.com\",\"buyerPayAmount\":\"0.01\",\"charset\":\"utf-8\",\"fundBillList\":\"[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]\",\"gmtCreate\":\"2017-01-17 09:23:15\",\"gmtPayment\":\"2017-01-17 09:23:17\",\"invoiceAmount\":\"0.01\",\"notifyId\":\"f04acb12a848012dd5a379bf7870d5ahba\",\"notifyTime\":\"2017-01-17 09:23:17\",\"notifyType\":\"trade_status_sync\",\"outTradeNo\":\"201701170923041283498\",\"pointAmount\":\"0.00\",\"receiptAmount\":\"0.01\",\"sellerEmail\":\"zyxt@myriadautos.com\",\"sellerId\":\"2088421552797501\",\"sign\":\"rNjzIDTEzvS0vvqfSyq0bE7qGdgExCB2SCCB0Sv3u0MPGPJmO5yuLVHw4mdioaknRsQ665A2Ypwv6p4kIBZGnyzRo3ZqcL2Q8vRr8e1OfTcSCDFdB4UuItQdOdeCmIutilVhUyY81eQnE+PT7RKG+i0EIJbYMWOYpNGW014jknQ=\",\"signType\":\"RSA\",\"subject\":\"中迪用车\",\"totalAmount\":\"0.01\",\"tradeNo\":\"2017011721001004170255249517\",\"tradeStatus\":\"TRADE_SUCCESS\",\"version\":\"1.0\"}";
//			AlipayNotification notice = JSON.parseObject(jsonString, AlipayNotification.class);
//			notice.setVerifyResult(verifyResult);
//			System.out.println("----------------------------------------------------------------------------------");
//			System.out.println(jsonString);
//			System.out.println("----------------------------------------------------------------------------------");
//			String resultResponse = "success";
//			PrintWriter printWriter = null;
//			try {
//				printWriter = response.getWriter();
//				
//				//do business
//				if(verifyResult){
//					accountOrderService.pay(notice.getOutTradeNo(), new BigDecimal(notice.getTotalFee()), "测试");
//				}
//				//fail due to verification error
//				else{
//					resultResponse = "fail";
//				}
//				
//			} catch (Exception e) {
////				logger.error("alipay notify error :", e);
//				e.printStackTrace();
//				resultResponse = "fail";
//				printWriter.close();
//			}
//			System.out.println(resultResponse);
//			
//			if (printWriter != null) {
//				printWriter.print(resultResponse);
//			}
//			
//		} catch (Exception e1) {
//		
//			e1.printStackTrace();
//		} 
	}
	
	public static void main(String[] args) {
		String s = "{\"channel\":\"alipay\",\"rawdata\":\"{\"result\":\"{\\\"alipay_trade_app_pay_response\\\":{\\\"code\\\":\\\"10000\\\",\\\"msg\\\":\\\"Success\\\",\\\"app_id\\\":\\\"2016082601807699\\\",\\\"auth_app_id\\\":\\\"2016082601807699\\\",\\\"charset\\\":\\\"utf-8\\\",\\\"timestamp\\\":\\\"2017-03-12 17:10:50\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"trade_no\\\":\\\"2017031221001004520246484183\\\",\\\"seller_id\\\":\\\"2088421552797501\\\",\\\"out_trade_no\\\":\\\"1489309838521875044\\\"},\\\"sign\\\":\\\"Tk5AFLOesnC2lG1JLUILcZMFMaAgM9rCSwRADyycOBxUT1fHHT8fsFbjsVjoyHP3uRoBNHyzoVQpzejhdL71/jCWFVJzqxyLBSDPh361Jc38jNN7GRAyThOI/nrU2Xp051OCI1fmzeun3jvSO03ZUFSRMHG3t6ID8PLuR25/bl0=\\\",\\\"sign_type\\\":\\\"RSA\\\"}\",\"resultStatus\":\"9000\",\"memo\":\"\"}\",\"description\":\"\"}";
	}
	
}
