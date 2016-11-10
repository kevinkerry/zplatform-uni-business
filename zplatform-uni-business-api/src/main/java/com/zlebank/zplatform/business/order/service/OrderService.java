package com.zlebank.zplatform.business.order.service;

import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.InstPayOrderBean;
import com.zlebank.zplatform.business.order.bean.OrderBean;
import com.zlebank.zplatform.business.order.bean.RefundOrderBean;
/***
 * 订单相关
 * @author liusm
 *
 */
public interface OrderService {
	/***
	 * 创建消费订单
	 * @param order
	 * @return
	 */
	public String createConsumeOrder(OrderBean order) throws BusinessOrderException;
	
	/***
	 * 创建退款订单
	 * @param order
	 * @return
	 */
	public String createRefundOrder(RefundOrderBean order) throws BusinessOrderException;
	/***
	 * 创建实时代付订单
	 * @param order
	 * @return
	 */
	public String createInsteadPayOrder(InstPayOrderBean order)throws BusinessOrderException;

}
