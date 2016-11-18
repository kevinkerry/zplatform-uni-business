package com.zlebank.zplatform.business.service.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessMemberException;
import com.zlebank.zplatform.business.member.bean.MemberBean;
import com.zlebank.zplatform.business.member.service.LoginPwdService;
import com.zlebank.zplatform.business.member.service.QueryMemberService;
import com.zlebank.zplatform.business.sms.bean.SmsBean;
import com.zlebank.zplatform.business.sms.service.SmsService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class LoginPwdServiceTest {
	@Autowired
	private LoginPwdService loginPwdService;
	@Autowired
	private SmsService smsService;
	@Test
	public void vaildatePwd(){
		try {
			ResultBean  bean =loginPwdService.vaildatePwd("100000000000640", "l123456");
			System.out.println(JSON.toJSONString(bean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void modifyPwd(){
		try {
			ResultBean  bean =loginPwdService.modifyPwd("100000000000640", "l123456", "l123456");
			System.out.println(JSON.toJSONString(bean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void sendSms(){
		SmsBean bean= new SmsBean();
		bean.setMobile("18210457410");
		bean.setModuleType("1003");
		ResultBean  result =smsService.sendSmsCode(bean);
		System.out.println(JSON.toJSONString(result));
	}
	@Test
	public void resetPwd(){
		try {
			ResultBean  bean =loginPwdService.resetPwd("100000000000640", "l123456", "l123456");
			System.out.println(JSON.toJSONString(bean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
