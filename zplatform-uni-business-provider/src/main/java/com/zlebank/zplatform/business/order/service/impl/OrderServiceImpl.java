package com.zlebank.zplatform.business.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.InstPayOrderBean;
import com.zlebank.zplatform.business.order.bean.OrderBean;
import com.zlebank.zplatform.business.order.bean.RefundOrderBean;
import com.zlebank.zplatform.business.order.bean.WapWithdrawBean;
import com.zlebank.zplatform.business.order.service.OrderService;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;
import com.zlebank.zplatform.payment.order.bean.SimpleOrderBean;
import com.zlebank.zplatform.payment.order.bean.WithdrawOrderBean;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private com.zlebank.zplatform.payment.order.service.OrderService orderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberOperationService memberOperationService;
    

	public String createConsumeOrder(OrderBean order) throws BusinessOrderException {
		try {
			if(order==null){
				throw new BusinessOrderException("BO0000");
			}
			SimpleOrderBean orderBean=BeanCopyUtil.copyBean(SimpleOrderBean.class, order);
			return this.orderService.createConsumeOrder(orderBean);
		}  catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessOrderException("BO0001");//创建消费订单失败
		}catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0002");//创建消费订单异常
		}
	}

	public String createRefundOrder(RefundOrderBean order)throws BusinessOrderException {
		try {
			if(order==null){
				throw new BusinessOrderException("BO0000");
			}
			com.zlebank.zplatform.payment.order.bean.RefundOrderBean refundOrderBean=
					BeanCopyUtil.copyBean(com.zlebank.zplatform.payment.order.bean.RefundOrderBean.class, order);
			return this.orderService.createRefundOrder(refundOrderBean);
		}  catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessOrderException("BO0003");//创建退款订单失败
		}catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0004");//创建退款订单异常
		}
	}

	public String createInsteadPayOrder(InstPayOrderBean order)throws BusinessOrderException {
			try {
				if(order==null){
					throw new BusinessOrderException("BO0000");
				}
				InsteadPayOrderBean orderBean=BeanCopyUtil.copyBean(InsteadPayOrderBean.class, order);
				return this.orderService.createInsteadPayOrder(orderBean);
			} catch (PaymentOrderException e) {
				 e.printStackTrace();
				 log.error(e.getMessage());
				 throw new BusinessOrderException("BO0005");//创建实时代付订单失败
			} catch (Exception e) {
				e.printStackTrace();
				 log.error(e.getMessage());
				 throw new BusinessOrderException("BO0006");//创建实时代付订单异常
			}
	
	}

	@Override
	public String recharge(OrderBean order) throws BusinessOrderException {
		try {
			if(order==null){
				throw new BusinessOrderException("BO0000");
			}
			SimpleOrderBean orderBean=BeanCopyUtil.copyBean(SimpleOrderBean.class, order);
			return this.orderService.createRechargeOrder(orderBean);
		} catch (PaymentOrderException e) {
			 e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0007");//创建充值订单失败
		} catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0008");//创建实充值订单异常
		}
	}

	@Override
	public String withdraw(WapWithdrawBean withdrawBean) throws BusinessOrderException {
		try {
			if(withdrawBean==null){
				throw new BusinessOrderException("BO0000");
			}
			WithdrawOrderBean withdrawOrderBean = BeanCopyUtil.copyBean(WithdrawOrderBean.class, withdrawBean);
			return this.orderService.createWithdrawOrder(withdrawOrderBean);
		} catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0009");//创建实充值订单异常
		}
	}

	@Override
	public String withdraw(WapWithdrawBean withdrawBean, String payPwd) throws BusinessOrderException {
		try {
			if(withdrawBean==null || withdrawBean.getMemberId() ==null ||payPwd==null){
				throw new BusinessOrderException("BO0000");
			}
			//校验支付密码
			//校验会员
			PoMemberBean pm = memberService.getMbmberByMemberId(withdrawBean.getMemberId(), MemberType.INDIVIDUAL);
			if(pm==null){
				 throw new BusinessOrderException("BO0010");//体现会员不存在
			}
			MemberBean member = new MemberBean();
			member.setLoginName(pm.getLoginName());
			member.setInstiId(pm.getInstiId());
			member.setPhone(pm.getPhone());
			member.setPaypwd(payPwd);
			Boolean flag= memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, member);
			if(!flag){
				throw new BusinessOrderException("BO0011");//校验支付密码失败
			}
			WithdrawOrderBean withdrawOrderBean = BeanCopyUtil.copyBean(WithdrawOrderBean.class, withdrawBean);
			return this.orderService.createWithdrawOrder(withdrawOrderBean);
		} catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0009");//创建实充值订单异常
		}
	}

	

}
