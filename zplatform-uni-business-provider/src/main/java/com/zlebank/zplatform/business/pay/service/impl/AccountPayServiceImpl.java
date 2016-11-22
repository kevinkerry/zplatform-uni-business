package com.zlebank.zplatform.business.pay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.OrderStatusEnum;
import com.zlebank.zplatform.business.commons.enums.SmsValidateEnum;
import com.zlebank.zplatform.business.exception.BusinessPayException;
import com.zlebank.zplatform.business.pay.bean.AccountPayBean;
import com.zlebank.zplatform.business.pay.service.AccountPayService;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberBankCardService;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.payment.exception.PaymentAccountPayException;
import com.zlebank.zplatform.payment.exception.PaymentOrderException;
import com.zlebank.zplatform.payment.exception.PaymentQuickPayException;
import com.zlebank.zplatform.payment.exception.PaymentRouterException;
import com.zlebank.zplatform.payment.order.bean.OrderResultBean;
import com.zlebank.zplatform.payment.order.service.QueryService;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("busAccountPayService")
public class AccountPayServiceImpl implements AccountPayService{
	private final static Logger log = LoggerFactory.getLogger(AccountPayServiceImpl.class);
	@Autowired
	private com.zlebank.zplatform.payment.accpay.service.AccountPayService accountPayService;
	@Autowired
	private ISMSService smsService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private QueryService queryService;
	@Autowired
	private MemberOperationService memberOperationService;
	@Override
	public ResultBean pay(AccountPayBean bean) throws BusinessPayException {
		com.zlebank.zplatform.payment.accpay.bean.AccountPayBean payBean =null;
		ResultBean resultBean = null;
		try {
			if(bean == null){
				return new ResultBean("BP0000", "参数不能为空！");
			}
			//根据TN校验订单信息
			OrderResultBean orderBean =this.queryService.queryOrderByTN(bean.getTn());
			if(orderBean == null){
				return new ResultBean("BP0006","查询订单失败");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.SUCCESS)){
				return new ResultBean("BP0007", "发送短信失败！此交易已支付");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.PAYING)){
				return new ResultBean("BP0008", "发送短信失败！此交易正支付中");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.FAILED)){
				return new ResultBean("BP009", "发送短信失败！此交易已超时");
			}
			//校验会员信息
		    PoMemberBean member = memberService.getMbmberByMemberId(bean.getMemberId(), MemberType.INDIVIDUAL);
		    if (member == null) {// 资金账户不存在
	        	return new ResultBean("BP0010", "查询会员失败");
	        }
			payBean = BeanCopyUtil.copyBean(com.zlebank.zplatform.payment.accpay.bean.AccountPayBean.class, bean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean  result =this.accountPayService.pay(payBean);
			if(result!=null){
				if(result.isResultBool()){
					 resultBean=new ResultBean(OrderStatusEnum.SUCCESS.getStatus());
				}else{
					resultBean= BeanCopyUtil.copyBean(ResultBean.class,result);
				}
			}else{
				resultBean = new ResultBean("BP0015", "账户支付失败！");
			}
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0001", "订单查询异常："+e.getMessage());
		}catch(PaymentAccountPayException e){
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0016", "账户支付异常："+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP00013", "支付异常！");
		}
		return resultBean;
	}

	@Override
	public ResultBean payBySmsCode(AccountPayBean bean, String smsCode) {
		com.zlebank.zplatform.payment.accpay.bean.AccountPayBean payBean =null;
		ResultBean resultBean = null;
		try {
			if(bean == null||smsCode==null){
				return new ResultBean("BP0000", "参数不能为空！");
			}
			//根据TN校验订单信息
			OrderResultBean orderBean =this.queryService.queryOrderByTN(bean.getTn());
			if(orderBean == null){
				return new ResultBean("BP0006","查询订单失败");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.SUCCESS)){
				return new ResultBean("BP0007", "发送短信失败！此交易已支付");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.PAYING)){
				return new ResultBean("BP0008", "发送短信失败！此交易正支付中");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.FAILED)){
				return new ResultBean("BP009", "发送短信失败！此交易已超时");
			}
			//校验会员信息
		    PoMemberBean member = memberService.getMbmberByMemberId(bean.getMemberId(), MemberType.INDIVIDUAL);
		    if (member == null) {// 资金账户不存在
	        	return new ResultBean("BP0010", "查询会员失败");
	        }
			//校验短信
			int vcode=0;
			try {
				 vcode =this.smsService.verifyCode(member.getPhone(), bean.getTn(), smsCode);
			} catch (Exception e) {
				e.getMessage();
				return new ResultBean("BP0005", "查询短信失败，请重新获取短信验证码");
			}
			SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
			if(valEnum != SmsValidateEnum.SV1){
				return new ResultBean("BP0005", valEnum.getMsg());
			}
			payBean = BeanCopyUtil.copyBean(com.zlebank.zplatform.payment.accpay.bean.AccountPayBean.class, bean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean  result =this.accountPayService.pay(payBean);
			if(result!=null){
				if(result.isResultBool()){
					 resultBean=new ResultBean(OrderStatusEnum.SUCCESS.getStatus());
				}else{
					resultBean= BeanCopyUtil.copyBean(ResultBean.class,result);
				}
			}else{
				resultBean = new ResultBean("BP0015", "账户支付失败！");
			}
		} catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0001", "订单查询异常："+e.getMessage());
		}catch(PaymentAccountPayException e){
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0016", "账户支付异常："+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP00013", "支付异常！");
		}
		return resultBean;
	}

	@Override
	public ResultBean payByPayPassword(AccountPayBean bean, String memberId, String payPassword) {
		com.zlebank.zplatform.payment.accpay.bean.AccountPayBean payBean =null;
		ResultBean resultBean = null;
		try {
			if(bean == null || bean.getTn()==null||payPassword==null){
				return new ResultBean("BP0000", "参数不能为空！");
			}
			//根据TN校验订单信息
			OrderResultBean orderBean =this.queryService.queryOrderByTN(bean.getTn());
			if(orderBean == null){
				return new ResultBean("BP0006","查询订单失败");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.SUCCESS)){
				return new ResultBean("BP0007", "发送短信失败！此交易已支付");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.PAYING)){
				return new ResultBean("BP0008", "发送短信失败！此交易正支付中");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.FAILED)){
				return new ResultBean("BP009", "发送短信失败！此交易已超时");
			}
			//校验会员信息
		   PoMemberBean member = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
	        if (member == null) {// 资金账户不存在
	        	return new ResultBean("BP0010", "查询会员失败");
	        }
		    MemberBean memberBean = new MemberBean();
	        memberBean.setLoginName(member.getLoginName());
	        memberBean.setInstiId(member.getInstiId());
	        memberBean.setPhone(member.getPhone());
	        memberBean.setPaypwd(payPassword);
	        boolean pwdFlag= this.memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, memberBean);
			if(!pwdFlag){
				return new ResultBean("BP0011", "支付密码校验失败");
			}
			//支付
			payBean = BeanCopyUtil.copyBean(com.zlebank.zplatform.payment.accpay.bean.AccountPayBean.class, bean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean  result =this.accountPayService.pay(payBean);
			if(result!=null){
				if(result.isResultBool()){
					 resultBean=new ResultBean(OrderStatusEnum.SUCCESS.getStatus());
				}else{
					resultBean= BeanCopyUtil.copyBean(ResultBean.class,result);
				}
			}else{
				resultBean = new ResultBean("BP0015", "账户支付失败！");
			}
		}catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0001", "订单查询异常："+e.getMessage());
		}catch(PaymentAccountPayException e){
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0016", "账户支付异常："+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP00013", "支付异常！");
		}
		return resultBean;
	}

	@Override
	public ResultBean pay(AccountPayBean bean, String memberId, String smsCode, String payPassword) {
		com.zlebank.zplatform.payment.accpay.bean.AccountPayBean payBean =null;
		ResultBean resultBean = null;
		try {
			if(bean == null || bean.getTn()==null||payPassword==null||smsCode==null){
				return new ResultBean("BP0000", "参数不能为空！");
			}
			//校验会员信息
		   PoMemberBean member = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
	        if (member == null) {// 资金账户不存在
	        	return new ResultBean("BP0010", "查询会员失败");
	        }
			//校验短信
			int vcode=0;
			try {
				 vcode =this.smsService.verifyCode(member.getPhone(), bean.getTn(), smsCode);
			} catch (Exception e) {
				e.getMessage();
				return new ResultBean("BP0005", "查询短信失败，请重新获取短信验证码");
			}
			SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
			if(valEnum != SmsValidateEnum.SV1){
				return new ResultBean("BP0005", valEnum.getMsg());
			}
			//根据TN校验订单信息
			OrderResultBean orderBean;
			orderBean = this.queryService.queryOrderByTN(bean.getTn());
			if(orderBean == null){
				return new ResultBean("BP0006","查询订单失败");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.SUCCESS)){
				return new ResultBean("BP0007", "发送短信失败！此交易已支付");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.PAYING)){
				return new ResultBean("BP0008", "发送短信失败！此交易正支付中");
			}else if(orderBean.getOrderStatus().equals(OrderStatusEnum.FAILED)){
				return new ResultBean("BP009", "发送短信失败！此交易已超时");
			}
			
		    MemberBean memberBean = new MemberBean();
	        memberBean.setLoginName(member.getLoginName());
	        memberBean.setInstiId(member.getInstiId());
	        memberBean.setPhone(member.getPhone());
	        memberBean.setPaypwd(payPassword);
	        boolean pwdFlag = this.memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, memberBean);
			if(!pwdFlag){
				return new ResultBean("BP0011", "支付密码校验失败");
			}
			//支付
			payBean = BeanCopyUtil.copyBean(com.zlebank.zplatform.payment.accpay.bean.AccountPayBean.class, bean);
			com.zlebank.zplatform.payment.commons.bean.ResultBean  result =this.accountPayService.pay(payBean);
			if(result!=null){
				if(result.isResultBool()){
					 resultBean=new ResultBean(OrderStatusEnum.SUCCESS.getStatus());
				}else{
					resultBean= BeanCopyUtil.copyBean(ResultBean.class,result);
				}
			}else{
				resultBean = new ResultBean("BP0015", "账户支付失败！");
			}
		}catch (PaymentOrderException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0001", "订单查询异常："+e.getMessage());
		}catch(PaymentAccountPayException e){
			log.error(e.getMessage());
			resultBean = new ResultBean("BP0016", "账户支付异常！"+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			resultBean = new ResultBean("BP00013", "支付异常！");
		}
		return resultBean;
	
	}
	
	
}
