package com.zlebank.zplatform.business.sms.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.sms.bean.SmsBean;
/***
 * 短信相关
 * @author liusm
 *
 */
public interface smsService {
	/****
	 * 非交易类型获取短信验证
	 * @param bean
	 * @param moduleType
	 * @return
	 */
	public ResultBean sendSmsCode(SmsBean bean) ;
	 
	/****
	 * 根据绑卡Id和手机号获取短信验证码
	 * @param bindId
	 * @param phone
	 * @return
	 */
	public ResultBean sendSmsCode(String bindId, String phone);
}
