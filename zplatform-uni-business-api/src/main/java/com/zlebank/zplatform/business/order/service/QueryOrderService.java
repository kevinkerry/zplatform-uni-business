package com.zlebank.zplatform.business.order.service;

import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.OrderResultBean;
/***
 * 查询订单相关
 * @author liusm
 *
 */
public interface QueryOrderService {
	/****
	 * 根据tn查询订单
	 * @param tn
	 * @return
	 */
	public OrderResultBean queryOrder(String tn ) throws BusinessOrderException;
	/***
	 * 根据商户和授理订单号查询订单
	 * @param merchId
	 * @param accOrderNo
	 * @return
	 */
	public OrderResultBean queryOrder(String merchId, String accOrderNo) throws BusinessOrderException;
	/***
	 * 
	 * @param merchId
	 * @param accOrderNo
	 * @return
	 * @throws BusinessOrderException
	 */
	public OrderResultBean queryInstPayOrder(String merchId, String accOrderNo)throws BusinessOrderException;
	
}
