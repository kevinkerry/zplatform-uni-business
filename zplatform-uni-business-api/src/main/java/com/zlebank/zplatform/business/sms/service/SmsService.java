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
	 * 交易类型获取短信验证
	 * 前提：不绑卡
	 * @param tn 
	 * @param bindId
	 * @param phone
	 * @return
	 */
	public ResultBean sendSmsCode(String tn , String phone);
}
