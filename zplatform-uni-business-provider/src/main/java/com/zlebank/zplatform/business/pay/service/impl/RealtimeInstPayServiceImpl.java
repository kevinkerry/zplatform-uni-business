package com.zlebank.zplatform.business.pay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.OrderStatusEnum;
import com.zlebank.zplatform.business.pay.bean.RealTimeInstPayBean;
import com.zlebank.zplatform.business.pay.service.RealtimeInstPayService;
import com.zlebank.zplatform.member.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.exception.PaymentInsteadPayException;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;
import com.zlebank.zplatform.payment.quickpay.service.RealTimeInsteadPayService;
@Service("busRealtimeInstPayService")
public class RealtimeInstPayServiceImpl  implements RealtimeInstPayService{
	private final static Logger log = LoggerFactory.getLogger(RealtimeInstPayServiceImpl.class);
	
	@Autowired
    private RealTimeInsteadPayService realtimeInstPayService;

	@Override
	public ResultBean pay(RealTimeInstPayBean bean) {
		ResultBean resultBean = null;
		try {
			if(bean == null){
				return new ResultBean("BP0000", "参数不能为空！");
			}
			InsteadPayOrderBean insteadPayOrderBean = new InsteadPayOrderBean();
			insteadPayOrderBean= BeanCopyUtil.copyBean(InsteadPayOrderBean.class, bean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean result =realtimeInstPayService.singleInsteadPay(insteadPayOrderBean);
			if(result!=null){
				if(result.isResultBool()){
					 resultBean=new ResultBean(OrderStatusEnum.SUCCESS.getStatus());
				}else{
					resultBean= BeanCopyUtil.copyBean(ResultBean.class,result);
				}
			}else{
				resultBean = new ResultBean("BP0017", "代付失败！");
			}
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0001", "订单查询异常！"+e.getMessage());
		} catch (PaymentInsteadPayException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0018", "代付异常！"+e.getMessage());
		} catch (PaymentQuickPayException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0003", "代付异常！"+e.getMessage());
		} catch (PaymentRouterException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0004", "路由信息异常！");
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP00013", "支付异常！");
		}
		return resultBean;
	}
	
	

}
