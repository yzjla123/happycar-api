package com.happycar.api.payment.wexin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.happycar.api.utils.MD5Util;
/**
 * @author 崔佳华
 * @date 2017年01月11日
 */
public class Wxpay {
	private static String appid = WeixinPayConstants.appid;  
	private static String partner = WeixinPayConstants.partner;  
	private static String partnerkey = WeixinPayConstants.partnerkey;
	private static String createOrderURL = WeixinPayConstants.createOrderURL;
	private static String notify_url = WeixinPayConstants.notify_url;
	UnifiedOrder unifiedOrder = null;
	public JsAPIConfig wxPay(String total_fee,String body,String out_trade_no,HttpServletRequest request) {
		float sessionmoney = Float.parseFloat(total_fee);  
        String finalmoney = String.format("%.2f", sessionmoney);  
        finalmoney = finalmoney.replace(".", "");  
        int intMoney = Integer.parseInt(finalmoney);              
        //总金额以分为单位，不带小数点  
        String order_total_fee = String.valueOf(intMoney);  
        unifiedOrder = new UnifiedOrder();
        unifiedOrder.setAppid(appid);
        unifiedOrder.setAttach("微信支付");
        unifiedOrder.setBody(body);
        unifiedOrder.setMch_id(partner);
        unifiedOrder.setNonce_str(PayCommonUtil.getNonceStr());
        unifiedOrder.setNotify_url(notify_url);
        unifiedOrder.setOut_trade_no(out_trade_no);
        unifiedOrder.setSpbill_create_ip(request.getRemoteAddr());
        unifiedOrder.setTotal_fee(order_total_fee);
        String sign = createUnifiedOrderSign(unifiedOrder);
        unifiedOrder.setSign(sign);
        String xml=XMLUtil.getXmlByUnifiedOrder(unifiedOrder);
        String response;
        JsAPIConfig config = new JsAPIConfig();
		try {
			response = HttpConnection.post(createOrderURL, xml);
			 Map<Object,Object> responseMap = XMLUtil.parseXml(response);
	         config=createPayConfig(responseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return config;
	}
	/**
     * 获取统一下单签名
     * @Title: createUnifiedOrderSign
     * @param @param unifiedOrder
     * @param @return    
     * @return String    
     * @throws
     */
    public String createUnifiedOrderSign(UnifiedOrder unifiedOrder){
        StringBuffer sign = new StringBuffer();
        sign.append("appid=").append(unifiedOrder.getAppid());
        sign.append("&attach=").append(unifiedOrder.getAttach());
        sign.append("&body=").append(unifiedOrder.getBody());
        sign.append("&mch_id=").append(unifiedOrder.getMch_id());
        sign.append("&nonce_str=").append(unifiedOrder.getNonce_str());
        sign.append("&notify_url=").append(unifiedOrder.getNotify_url());
        sign.append("&out_trade_no=").append(unifiedOrder.getOut_trade_no());
        sign.append("&spbill_create_ip=").append(unifiedOrder.getSpbill_create_ip());
        sign.append("&total_fee=").append(unifiedOrder.getTotal_fee());
        sign.append("&trade_type=").append(unifiedOrder.getTrade_type());
        sign.append("&key=").append(partnerkey);
        return MD5Util.MD5Encode(sign.toString(),"UTF-8").toUpperCase();
    }

    /**
     * 获取支付配置
     * @Title: createPayConfig
     * @param @param preayId 统一下单prepay_id
     * @param @return
     * @param @throws Exception    
     * @return JsAPIConfig    
     * @throws
     */
    public JsAPIConfig createPayConfig(Map<Object,Object> responseMap) throws Exception {
        JsAPIConfig config = new JsAPIConfig();
        String timestamp = PayCommonUtil.getTimeStamp();
        StringBuffer sign = new StringBuffer();
        sign.append("appid=").append(responseMap.get("appid").toString());
        sign.append("&noncestr=").append(unifiedOrder.getNonce_str());
        sign.append("&package=").append("Sign=WXPay");
        sign.append("&partnerid=").append(partner);
        sign.append("&prepayid=").append(responseMap.get("prepay_id").toString());
        sign.append("&timestamp=").append(timestamp);
        sign.append("&key=").append(partnerkey);
        String signature =  MD5Util.MD5Encode(sign.toString(),"UTF-8").toUpperCase();
        config.setAppid(appid);
        config.setAppkey(partnerkey);
        config.setTimestamp(timestamp);
        config.setPackageName("Sign=WXPay");
        config.setNoncestr(unifiedOrder.getNonce_str());
        config.setSign(signature);
        config.setPartnerid(partner);
        config.setPrepayid(responseMap.get("prepay_id").toString());
        return config;
    }

}
