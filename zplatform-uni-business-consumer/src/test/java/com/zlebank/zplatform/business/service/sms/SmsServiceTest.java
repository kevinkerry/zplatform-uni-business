package com.zlebank.zplatform.business.service.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.exception.BusinessRealnameException;
import com.zlebank.zplatform.business.exception.BusinessSmsException;
import com.zlebank.zplatform.business.order.bean.OrderResultBean;
import com.zlebank.zplatform.business.order.service.QueryOrderService;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;
import com.zlebank.zplatform.business.realname.service.RealnameService;
import com.zlebank.zplatform.business.sms.service.SmsService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class SmsServiceTest {
	
	@Autowired
	private SmsService smsService;
	@Test
	public void sendSms(){
		try {
			String tn ="161118061500000596";
			String phone="18210457410";
			ResultBean bean =smsService.sendSmsCode(tn,phone);
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void sendSmsByCard(){
		try {
			String tn ="161118061500000596";
			String phone="18210457410";
			String cardNo="6217730706921466";
			ResultBean bean =smsService.sendSmsCode(tn,phone,cardNo);
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
