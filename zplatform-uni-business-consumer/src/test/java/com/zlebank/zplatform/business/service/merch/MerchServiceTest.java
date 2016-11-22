package com.zlebank.zplatform.business.service.merch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.business.exception.BusinessMerchException;
import com.zlebank.zplatform.business.merch.service.MerchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class MerchServiceTest {
	@Autowired
	private MerchService merchService;
	@Test
	public void updateMerchPubKey(){
		try {
			Boolean flag=this.merchService.updateMerchPubKey("", "");
			System.out.println(flag);
		} catch (BusinessMerchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void resetPayPwd(){
		try {
			Boolean flag=this.merchService.resetPayPwd("200000000000615", "123456");
			System.out.println(flag);
		} catch (BusinessMerchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
