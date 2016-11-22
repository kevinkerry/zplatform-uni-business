package com.zlebank.zplatform.business.pay.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessPayException;
import com.zlebank.zplatform.business.pay.bean.AccountPayBean;

public interface AccountPayService {
	/****
	 * 账户余额支付(无短信验证、无支付密码)
	 * @param bean
	 * @return
	 */
     public ResultBean pay (AccountPayBean bean) throws  BusinessPayException ;
     /***
      * 账户余额支付(无支付密码)
      * @param bean
      * @param smsCode
      * @return
      */
     public ResultBean payBySmsCode(AccountPayBean bean , String smsCode);
     
     /****
      * 账户余额支付(无短信)
      * @param bean
      * @param payPassword
      * @return
      */
     public ResultBean payByPayPassword(AccountPayBean bean ,String memberId, String payPassword);
     /****
      * 账户余额支付(二者都有)
      * @param bean
      * @param smsCode
      * @param payPassword
      * @return
      */
     public ResultBean pay(AccountPayBean bean ,String memberId,String smsCode, String payPassword);
}
