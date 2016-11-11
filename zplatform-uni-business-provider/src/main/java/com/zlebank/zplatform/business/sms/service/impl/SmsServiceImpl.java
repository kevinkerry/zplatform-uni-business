package com.zlebank.zplatform.business.sms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.OrderStatusEnum;
import com.zlebank.zplatform.business.commons.enums.SmsResultEnum;
import com.zlebank.zplatform.business.exception.BusinessSmsException;
import com.zlebank.zplatform.business.realname.service.impl.RealnameServiceImpl;
import com.zlebank.zplatform.business.sms.bean.SmsBean;
import com.zlebank.zplatform.business.sms.service.SmsService;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.bean.OrderResultBean;
import com.zlebank.zplatform.payment.order.service.OrderService;
import com.zlebank.zplatform.payment.order.service.QueryService;
import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("smsService")
public class SmsServiceImpl implements SmsService{
	private final static Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
	@Autowired
	private ISMSService smsService;
	@Autowired
	private QueryService queryService;
	
	@Override
	public ResultBean sendSmsCode(SmsBean bean) {
		if(bean ==null || bean.getMobile()==null || bean.getModuleType() ==null){
			return  new ResultBean("BS0001", "参数不能为空！");
		}
		ModuleTypeEnum moduleType = ModuleTypeEnum.fromValue(bean.getModuleType());
		if(moduleType .equals( ModuleTypeEnum.UNKNOW)){
			return  new ResultBean("BS0002", "请检查短息模板类型！");
		}
		int smsResult = this.smsService.sendSMS(moduleType, bean.getMobile(),"" ,"" );
		SmsResultEnum smsEnum= SmsResultEnum.fromValue(String.valueOf(smsResult));
		if(smsEnum != SmsResultEnum.S100){
			return  new ResultBean("BS0003", smsEnum.getMsg());
		}
		return new ResultBean(true);
	}


	@Override
	public ResultBean sendSmsCode(String tn, String phone) {
		try {
			//校验参数是否可以为空
			if(StringUtil.isEmpty(tn)||StringUtil.isEmpty(phone)){
				return  new ResultBean("BS0001", "参数不能为空！");
			}
			//校验tn
			OrderResultBean orderBean = this.queryService.queryOrderByTN(tn);
			if(orderBean == null){
				return new ResultBean("BS0002", "根据TN查不到相应的交易");
			}
			if(orderBean.getOrderStatus().equals(OrderStatusEnum.SUCCESS)){
				return new ResultBean("BS0003", "发送短信失败！此交易已支付");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.PAYING)){
				return new ResultBean("BS0004", "发送短信失败！此交易正支付中");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.FAILED)){
				return new ResultBean("BS0005", "发送短信失败！此交易已超时");
			}
			//发送短信
			int smsResult = this.smsService.sendSMS(ModuleTypeEnum.ACCOUNTPAY, phone, tn, "");
			SmsResultEnum smsEnum= SmsResultEnum.fromValue(String.valueOf(smsResult));
			if(smsEnum != SmsResultEnum.S100){
				return  new ResultBean("BS0003", smsEnum.getMsg());
			}
			return new ResultBean(true);
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BS0006", "查询订单失败");
		}
	
	}

}
