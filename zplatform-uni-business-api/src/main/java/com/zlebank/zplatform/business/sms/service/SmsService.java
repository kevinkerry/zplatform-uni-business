package com.zlebank.zplatform.business.sms.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.sms.bean.SmsBean;
/***
 * 短信相关
 * @author liusm
 *
 */
public interface SmsService {
	/****
	 * 非交易类型获取短信验证
	 * @param bean
	 * @param moduleType
	 * @return
	 */
	public ResultBean sendSmsCode(SmsBean bean) ;
	 
	/****
	 * 交易获取短信验证
	 * 匿名支付和账户余额支付发送短信
	 * modulType-1006
	 * @param tn 
	 * @param phone
	 * @return
	 */
	public ResultBean sendSmsCode(String tn, String phone);
	
	/****
	 * 交易获取短信验证
	 * 快捷支付发送短信
	 * modulType-1002
	 * @param tn 
	 * @param phone
	 * @param cardNo
	 * @return
	 */
	public ResultBean sendSmsCode(String tn, String phone,String cardNo);
}
