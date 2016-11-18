package com.zlebank.zplatform.business.service.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.member.bean.RegisterMemberBean;
import com.zlebank.zplatform.business.member.service.MemberService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class MemberServiceTest {
	@Autowired
	private MemberService memberService;
	@Test
	public void register(){
		try {
			RegisterMemberBean bean = new RegisterMemberBean();
			bean.setCoopInstiCode("300000000000027");
			bean.setLoginName("liusmm");
			bean.setMemberName("liusm");
			bean.setMemberStatus("00");
			bean.setMemberType("01");
			bean.setPhone("18210457413");
			bean.setRegisterIdent("01");
			bean.setPwd("l123456");
			bean.setOperUserId(0);
			ResultBean result=memberService.register(bean);
			System.out.println(JSON.toJSONString(result));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void login(){
		try {
			ResultBean bean =memberService.login("liushoumei", "l123456", "300000000000027");
			System.out.println(JSON.toJSON(bean));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
}
