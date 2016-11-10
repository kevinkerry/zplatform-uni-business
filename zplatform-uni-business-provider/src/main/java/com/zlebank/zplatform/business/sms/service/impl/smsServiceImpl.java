package com.zlebank.zplatform.business.sms.service.impl;

import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.sms.bean.SmsBean;
import com.zlebank.zplatform.business.sms.service.smsService;
@Service("smsService")
public class smsServiceImpl implements smsService{
	
	
	@Override
	public ResultBean sendSmsCode(SmsBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean sendSmsCode(String bindId, String phone) {
		// TODO Auto-generated method stub
		return null;
	}

}
