package com.zlebank.zplatform.business.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.InstPayOrderBean;
import com.zlebank.zplatform.business.order.bean.OrderBean;
import com.zlebank.zplatform.business.order.bean.RefundOrderBean;
import com.zlebank.zplatform.business.order.service.OrderService;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;
import com.zlebank.zplatform.payment.order.bean.SimpleOrderBean;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private com.zlebank.zplatform.payment.order.service.OrderService orderService;

	public String createConsumeOrder(OrderBean order) throws BusinessOrderException {
		SimpleOrderBean orderBean=null;
		try {
			BeanUtils.copyProperties(order, orderBean);
			return this.orderService.createConsumeOrder(orderBean);
		}  catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessOrderException("BE0000");//订单服务异常
		}
	}

	public String createRefundOrder(RefundOrderBean order)throws BusinessOrderException {
		// TODO Auto-generated method stub
		return null;
	}

	public String createInsteadPayOrder(InstPayOrderBean order)throws BusinessOrderException {
			InsteadPayOrderBean orderBean = null;
			try {
				BeanUtils.copyProperties( order,orderBean);
				return this.orderService.createInsteadPayOrder(orderBean);
			} catch (PaymentOrderException e) {
				 e.printStackTrace();
				 log.error(e.getMessage());
				 throw new BusinessOrderException("BE0000");//生成订单失败
			}
	
	}

	

}
