package com.happycar.api.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happycar.api.contant.Constant;
import com.happycar.api.dao.PayOrderDao;


@Service
public class PayOrderService {
	private Logger logger = Logger.getLogger(PayOrderService.class);
	@Autowired
	private PayOrderDao payOrderDao;
//	@Autowired
//	private PassengerDao passengerDao;
//	@Autowired
//	private FundLogDao fundLogDao;
//	@Autowired
//	private SelfdriveOrderService selfdriveOrderService;

	@Transactional
	public boolean pay(String orderNo, String amount, String memo) {
//		AirPayOrder airPayOrder = payOrderDao.findByOrderNoAndStatus(orderNo,0);
//		if(airPayOrder==null){
//			logger.error("Can`t found payOrder by orderNo,OrderNo is:"+orderNo);
//			return false;
//		}
//		if(!airPayOrder.getAmount().equals(amount)){
//			logger.error("Amount is not eqaul[payOrder amount:"+airPayOrder.getAmount()+"callback amount:"+amount+"]");
//			return false;
//		}
//		airPayOrder.setStatus(1);
//		airPayOrder.setOrderTime(new Date());
//		AirPassenger passenger = passengerDao.findOne(airPayOrder.getPid());
//		passenger.setAmount(passenger.getAmount()+Float.parseFloat(amount));
//		passengerDao.save(passenger);
//		AirFundLog fundLog = new AirFundLog();
//		fundLog.setPid(passenger.getId());
//		fundLog.setAmount(Float.parseFloat(amount));
//		fundLog.setBalance(passenger.getAmount());
//		fundLog.setRid(airPayOrder.getId());
//		fundLog.setType(airPayOrder.getType());
//		fundLog.setAddTime(new Date());
//		fundLog.setRemark("余额充值");
//		fundLogDao.save(fundLog);
//		//保证金转入
//		if(Constant.PAY_TYPE_BOND==airPayOrder.getType().intValue()){
//			passenger.setBondAmount(passenger.getBondAmount()+Float.parseFloat(amount));
//			passengerDao.save(passenger);
//		}else if(Constant.PAY_TYPE_SELFORDER_PAY==airPayOrder.getType().intValue()){
//			//订单支付
//			boolean result = selfdriveOrderService.payOrder(Integer.parseInt(airPayOrder.getRid()));
//			if(!result) return false;
//		}
		return true;
	}

}
