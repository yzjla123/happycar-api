package com.happycar.api.controller.member;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.annotation.Authentication;
import com.happycar.api.controller.BaseController;
import com.happycar.api.dao.PayOrderDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcPayOrder;
import com.happycar.api.payment.alipay.Alipay;
import com.happycar.api.payment.wexin.JsAPIConfig;
import com.happycar.api.payment.wexin.Wxpay;
import com.happycar.api.utils.MessageUtil;
import com.happycar.api.vo.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * http://demo.dcloud.net.cn/payment/wxpay/?total=10
 * {"retcode":0,"retmsg":"ok","appid":"wx0411fa6a39d61297","noncestr":"0c9867ebf738f3b4597331a69bcac33a","package":"Sign=WXPay","partnerid":"1230636401","prepayid":"32010330001703104bd48d266fac0065","timestamp":1489124239,"sign":"27840763d67c07c44fcdac0046911ea4af157929"}
 * http://demo.dcloud.net.cn/payment/alipay/?total=10
 * service="mobile.securitypay.pay"&partner="2088801273866834"&_input_charset="UTF-8"&out_trade_no="20170310054014"&subject="DCloud项目捐赠"&payment_type="1"&seller_id="payservice@dcloud.io"&total_fee="10"&body="DCloud致力于打造HTML5最好的移动开发工具，包括终端的Runtime、云端的服务和IDE，同时提供各项配套的开发者服务。"&it_b_pay="1d"&notify_url="http%3A%2F%2Fdemo.dcloud.net.cn%2Fpayment%2Falipay%2Fnotify.php"&show_url="http%3A%2F%2Fwww.dcloud.io%2Fhelloh5%2F"&sign="VOvfU84c3A8TVvXcXLerh81dnKrT0FDgf6F6y%2F2wFzn3jFH59hlWc84Y3Vfw1P6Dax2xXiDgcVzkYCvXtBhm9%2BersbQaAnt6FTWq6yyKRn3%2BhiPyurFNW0jon1tOcSL1mbzLSY5nJWjquHCQ7U%2FuhBMPir%2FlE628oHaelzgwHRA%3D"&sign_type="RSA"
 * @author zhengjy
 *
 */
@Api(value = "支付管理")
@RestController
@RequestMapping("/api/member/payment")	
@Authentication
public class PaymentController extends BaseController{
	
	private Logger logger = Logger.getLogger(PaymentController.class);
	@Resource
	private PayOrderDao payOrderDao;
	
	@ApiOperation(value = "创建支付订单", httpMethod = "POST", notes = "创建支付订单")
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "rid", value = "相关业务订单id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "amount", value = "金额", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型 1充值 2自驾订单支付 3违章押金（保证金）4信用卡验证 5违章支付", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "channel", value = "支付通道 alipay 支付宝,yeepay 易宝,wxpay 微信,manage 管理员，epos 易宝无卡支付", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "source", value = "来源 1:微信小程序,2:微信公众号,3:安卓,4:iOS", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "accessToken", value = "token", required = true, dataType = "String", paramType = "query"),
	})
	@ApiResponses(value={
			@ApiResponse(code = 200, message = "")
	})
	@Transactional
	public ResponseModel createOrder(Integer rid,Float amount,Integer type,String channel,Integer source,HttpServletRequest request){
		ResponseModel model = new ResponseModel();
		HcMember member = getLoginMember(request);
		String orderNo = System.currentTimeMillis()+""+(int)((Math.random()*9+1)*100000);
		HcPayOrder order = new HcPayOrder();
		order.setAddTime(new Date());
		order.setAmount(amount);
		order.setChannel(channel);
		order.setOrderNo(orderNo);
		order.setOrderTime(new Date());
		order.setPid(member.getId());
		order.setRid(rid);
		order.setStatus(0);
		order.setType(type);
		order.setSource(source);
		payOrderDao.save(order);
		if("0".equals(channel)){
			String params = new Alipay().getSign(getBodyByType(type), getBodyByType(type), String.valueOf(amount), orderNo);
			model.addAttribute("data", params);
		}else if("1".equals(channel)){
			JsAPIConfig jsAPIConfig = new Wxpay().wxPay(String.valueOf(amount), getBodyByType(type), orderNo, request);
			model.addAttribute("data", jsAPIConfig);
		}
		MessageUtil.success("生成成功", model);
		return model;
	}

	private String getBodyByType(int type){
		if(type==1){
			return "充值";
		}else if(type==2){
			return "学费支付";
		}
		return "充值";
	}
}
