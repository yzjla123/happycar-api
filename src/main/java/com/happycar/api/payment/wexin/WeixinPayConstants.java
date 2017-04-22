package com.happycar.api.payment.wexin;

public class WeixinPayConstants {  
	/**
	 * 中迪
	 */
    public static final String appid = "wx7ef9a8305137d8e4";//在微信开发平台登记的app应用  
    public static final String partner = "1384440102";//商户号  
    public static final String partnerkey ="feae270e482f98a5b3htlc1d851bdab7";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，  
    /**
     * 中交
     */
//    public static final String appid = "wx6da8900c3d22f330";//在微信开发平台登记的app应用  
//    public static final String partner = "1288650101";//商户号  
//    public static final String partnerkey ="5LR16rjCy3jhn5DIl6Td802gtfSk9jrA";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，  

    public static final String createOrderURL="https://api.mch.weixin.qq.com/pay/unifiedorder";  
    public static final String notify_url="http://api.zhongdiyongche.com/Api/WeiXinPay/notify";//异步通知地址 
    
    
}  
