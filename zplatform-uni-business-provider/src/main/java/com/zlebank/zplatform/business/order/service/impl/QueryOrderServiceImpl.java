package com.zlebank.zplatform.business.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.OrderResultBean;
import com.zlebank.zplatform.business.order.service.QueryOrderService;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.service.QueryService;
@Service("queryOrderService")
public class QueryOrderServiceImpl implements QueryOrderService{
	private final static Logger log = LoggerFactory.getLogger(QueryOrderServiceImpl.class);
	@Autowired
	private QueryService queryService;
	@Override
	public OrderResultBean queryOrder(String tn) throws BusinessOrderException{
		OrderResultBean order = null;
		try {
			com.zlebank.zplatform.payment.order.bean.OrderResultBean orderBean =this.queryService.queryOrderByTN(tn);
			BeanUtils.copyProperties(orderBean, order);
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessOrderException("BE0001");
		}
		return order;
	}

	@Override
	public OrderResultBean queryOrder(String merchId, String accOrderNo) throws BusinessOrderException {
		OrderResultBean order = null;
		try {
			com.zlebank.zplatform.payment.order.bean.OrderResultBean orderBean =this.queryService.queryOrder(merchId, accOrderNo);
			BeanUtils.copyProperties(orderBean, order);
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessOrderException("BE0001");
		}
		return order;
		
	}

	@Override
	public OrderResultBean queryInstPayOrder(String merchId, String accOrderNo) throws BusinessOrderException {
		OrderResultBean order = null;
		try {
			com.zlebank.zplatform.payment.order.bean.OrderResultBean orderBean =this.queryService.queryInsteadPayOrder(merchId, accOrderNo);
			BeanUtils.copyProperties(orderBean, order);
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessOrderException("BE0001");
		}
		return order;
	}

}
