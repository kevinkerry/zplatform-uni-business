package com.zlebank.zplatform.business.quickPay.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.quickPay.bean.RealTimeInstPayBean;

public interface RealtimeInstPay {
	/****
	 * 快捷支付(无短信验证、无支付密码)
	 * @param bean
	 * @return
	 */
     public ResultBean pay (RealTimeInstPayBean bean);
}
