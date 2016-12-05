package com.zlebank.zplatform.business.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.dao.TxncodeDefDAO;
import com.zlebank.zplatform.business.commons.enums.BusiTypeEnum;
import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.InstPayOrderBean;
import com.zlebank.zplatform.business.order.bean.OrderBean;
import com.zlebank.zplatform.business.order.bean.RefundOrderBean;
import com.zlebank.zplatform.business.order.bean.WapWithdrawBean;
import com.zlebank.zplatform.business.order.service.OrderService;
import com.zlebank.zplatform.business.pojo.PojoTxncodeDef;
import com.zlebank.zplatform.member.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.order.bean.InsteadPayOrderBean;
import com.zlebank.zplatform.payment.order.bean.SimpleOrderBean;
import com.zlebank.zplatform.payment.order.bean.WithdrawOrderBean;

@Service("busOrderService")
public class OrderServiceImpl implements OrderService{
	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private com.zlebank.zplatform.payment.order.service.OrderService orderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberOperationService memberOperationService;
	@Autowired
    private TxncodeDefDAO txncodDefDao;

	public ResultBean createConsumeOrder(OrderBean order) throws BusinessOrderException {
		try {
			if(order==null || order.getTxnSubType()==null|| order.getTxnType()==null|| order.getBizType()==null){
				throw new BusinessOrderException("BO0000");
			}
			SimpleOrderBean orderBean=BeanCopyUtil.copyBean(SimpleOrderBean.class, order);
			//校验产品代码
			PojoTxncodeDef busicode=this.txncodDefDao.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
		    if(busicode==null){
		    	return new ResultBean("BO0016","交易类型不存在！");
		    }
		    if(!busicode.getBusitype().equals(BusiTypeEnum.CONSUME.getBusiType())){
		    	return new ResultBean("BO0017","只处理消费业务");
		    }
			String tn= this.orderService.createConsumeOrder(orderBean);
		    return new ResultBean(tn);
		}  catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BO0001", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0002");//创建消费订单异常
		}
	}

	public ResultBean createRefundOrder(RefundOrderBean order)throws BusinessOrderException {
		try {
			if(order==null || order.getTxnSubType()==null|| order.getTxnType()==null|| order.getBizType()==null){
				throw new BusinessOrderException("BO0000");
			}
			//校验产品代码
			PojoTxncodeDef busicode=this.txncodDefDao.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
		    if(busicode==null){
		    	return new ResultBean("BO0016","交易类型不存在！");
		    }
		    if(!busicode.getBusitype().equals(BusiTypeEnum.REFUND.getBusiType())){
		    	return new ResultBean("BO0018","只处理退款业务");
		    }
			com.zlebank.zplatform.payment.order.bean.RefundOrderBean refundOrderBean=
					BeanCopyUtil.copyBean(com.zlebank.zplatform.payment.order.bean.RefundOrderBean.class, order);
			String tn= this.orderService.createRefundOrder(refundOrderBean);
			return new ResultBean(tn);
		}  catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BO0003", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0004");//创建退款订单异常
		}
	}

	public ResultBean createInsteadPayOrder(InstPayOrderBean order)throws BusinessOrderException {
			try {
				if(order==null || order.getTxnSubType()==null|| order.getTxnType()==null|| order.getBizType()==null){
					throw new BusinessOrderException("BO0000");
				}
				//校验产品代码
				PojoTxncodeDef busicode=this.txncodDefDao.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
			    if(busicode==null){
			    	return new ResultBean("BO0016","交易类型不存在！");
			    }
			    if(!busicode.getBusitype().equals(BusiTypeEnum.INSTEADPAY.getBusiType())){
			    	return new ResultBean("BO0019","只处理退款业务");
			    }
				InsteadPayOrderBean orderBean=BeanCopyUtil.copyBean(InsteadPayOrderBean.class, order);
				String tn=this.orderService.createInsteadPayOrder(orderBean);
				 return new ResultBean(tn);
			} catch (PaymentOrderException e) {
				 e.printStackTrace();
				 log.error(e.getMessage());
				 return new ResultBean("BO0005", e.getMessage());//创建实时代付订单失败
			} catch (Exception e) {
				e.printStackTrace();
				 log.error(e.getMessage());
				 throw new BusinessOrderException("BO0006");//创建实时代付订单异常
			}
	
	}

	@Override
	public ResultBean recharge(OrderBean order) throws BusinessOrderException {
		try {
			if(order==null || order.getTxnSubType()==null|| order.getTxnType()==null|| order.getBizType()==null){
				throw new BusinessOrderException("BO0000");
			}
			//校验产品代码
			PojoTxncodeDef busicode=this.txncodDefDao.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
		    if(busicode==null){
		    	return new ResultBean("BO0016","交易类型不存在！");
		    }
		    if(!busicode.getBusitype().equals(BusiTypeEnum.RECHARGE.getBusiType())){
		    	return new ResultBean("BO0020","只处理充值业务");
		    }
			SimpleOrderBean orderBean=BeanCopyUtil.copyBean(SimpleOrderBean.class, order);
			String tn= this.orderService.createRechargeOrder(orderBean);
			return new ResultBean(tn);
		} catch (PaymentOrderException e) {
			 e.printStackTrace();
			 log.error(e.getMessage());
			 return new ResultBean("BO0007", e.getMessage());//创建充值订单失败
		} catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0008");//创建实充值订单异常
		}
	}

	@Override
	public ResultBean withdraw(WapWithdrawBean withdrawBean) throws BusinessOrderException {
		try {
			if(withdrawBean==null || withdrawBean.getTxnSubType()==null|| withdrawBean.getTxnType()==null|| withdrawBean.getBizType()==null){
				throw new BusinessOrderException("BO0000");
			}
			//校验产品代码
			PojoTxncodeDef busicode=this.txncodDefDao.getBusiCode(withdrawBean.getTxnType(), withdrawBean.getTxnSubType(), withdrawBean.getBizType());
		    if(busicode==null){
		    	return new ResultBean("BO0016","交易类型不存在！");
		    }
		    if(!busicode.getBusitype().equals(BusiTypeEnum.WITHDRAWALS.getBusiType())){
		    	return new ResultBean("BO0021","只处理提现业务");
		    }
			WithdrawOrderBean withdrawOrderBean = BeanCopyUtil.copyBean(WithdrawOrderBean.class, withdrawBean);
			String tn= this.orderService.createWithdrawOrder(withdrawOrderBean);
			return new ResultBean(tn);
		} catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0009");//创建实充值订单异常
		}
	}

	@Override
	public ResultBean withdraw(WapWithdrawBean withdrawBean, String payPwd) throws BusinessOrderException {
		try {
			if(withdrawBean==null || withdrawBean.getTxnSubType()==null
					|| withdrawBean.getTxnType()==null|| withdrawBean.getBizType()==null
					||withdrawBean.getMemberId() ==null ||payPwd==null){
				throw new BusinessOrderException("BO0000");
			}
			//校验产品代码
			PojoTxncodeDef busicode=this.txncodDefDao.getBusiCode(withdrawBean.getTxnType(), withdrawBean.getTxnSubType(), withdrawBean.getBizType());
		    if(busicode==null){
		    	return new ResultBean("BO0016","交易类型不存在！");
		    }
		    if(!busicode.getBusitype().equals(BusiTypeEnum.WITHDRAWALS.getBusiType())){
		    	return new ResultBean("BO0021","只处理提现业务");
		    }
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
			String tn =this.orderService.createWithdrawOrder(withdrawOrderBean);
			return new ResultBean(tn);
		} catch (Exception e) {
			e.printStackTrace();
			 log.error(e.getMessage());
			 throw new BusinessOrderException("BO0009");//创建实充值订单异常
		}
	}

	

}
