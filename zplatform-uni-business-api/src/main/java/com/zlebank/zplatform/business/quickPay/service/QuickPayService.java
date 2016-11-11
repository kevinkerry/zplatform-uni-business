package com.zlebank.zplatform.business.quickPay.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessPayException;
import com.zlebank.zplatform.business.quickPay.bean.PayBean;

/***
 * 支付相关
 * @author liusm
 *
 */
public interface QuickPayService {
	/****
	 * 快捷支付(无短信验证、无支付密码)
	 * @param bean
	 * @return
	 */
     public ResultBean pay (PayBean bean) throws  BusinessPayException ;
     /***
      * 快捷支付(无支付密码)
      * @param bean
      * @param smsCode
      * @return
      */
     public ResultBean payBySmsCode(PayBean bean , String smsCode);
     
     /****
      * 快捷支付(无短信)
      * @param bean
      * @param payPassword
      * @return
      */
     public ResultBean payByPayPassword(PayBean bean ,String memberId, String payPassword);
     /****
      * 快捷支付(二者都有)
      * @param bean
      * @param smsCode
      * @param payPassword
      * @return
      */
     public ResultBean pay(PayBean bean ,String memberId,String smsCode, String payPassword);
     
     
     
     
}
