package com.zlebank.zplatform.business.pay.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.pay.bean.RealTimeInstPayBean;

public interface RealtimeInstPayService {
	/****
	 * 单笔支付(无短信验证、无支付密码)
	 * @param bean
	 * @return
	 */
     public ResultBean pay (RealTimeInstPayBean bean);
}
