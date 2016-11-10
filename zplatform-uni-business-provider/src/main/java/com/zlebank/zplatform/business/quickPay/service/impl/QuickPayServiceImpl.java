package com.zlebank.zplatform.business.quickPay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessPayException;
import com.zlebank.zplatform.business.quickPay.bean.PayBean;
import com.zlebank.zplatform.business.quickPay.service.QuickPayService;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
@Service("quickPayService")
public class QuickPayServiceImpl implements QuickPayService{
	
	private final static Logger log = LoggerFactory.getLogger(QuickPayServiceImpl.class);
	
	@Autowired
	private com.zlebank.zplatform.payment.quickpay.service.QuickPayService quickPayService;
	
	@Override
	public ResultBean pay(PayBean bean) throws BusinessPayException {
		com.zlebank.zplatform.payment.quickpay.bean.PayBean payBean =null;
		ResultBean resultBean = null;
		try {
			BeanUtils.copyProperties(bean, payBean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean  result =this.quickPayService.pay(payBean);
			if(result!=null){
				BeanUtils.copyProperties(result, resultBean);
			}
		} catch (PaymentQuickPayException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessPayException("BP0000");//支付异常
		} catch (PaymentRouterException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessPayException("BP0001");//路由信息异常
		}
		return resultBean;
	}

	@Override
	public ResultBean payBySmsCode(PayBean bean, String smsCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean payByPayPassword(PayBean bean, String payPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean pay(PayBean bean, String smsCode, String payPassword) {
		// TODO Auto-generated method stub
		return null;
	}

}
