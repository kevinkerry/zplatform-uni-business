package com.zlebank.zplatform.business.service.pay;

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
import com.zlebank.zplatform.business.pay.bean.AccountPayBean;
import com.zlebank.zplatform.business.pay.bean.PayBean;
import com.zlebank.zplatform.business.pay.service.AccountPayService;
import com.zlebank.zplatform.business.pay.service.QuickPayService;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;
import com.zlebank.zplatform.business.realname.service.RealnameService;
import com.zlebank.zplatform.business.sms.service.SmsService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class AccountpayServiceTest {
	
	@Autowired
	private AccountPayService  accountPayService;
	@Test
	public void payBySmsCode(){
		/*try {
			AccountPayBean account = new AccountPayBean();
			account.setMemberId(memberId);
			account.setTn(tn);
			account.setTxnAmt(txnAmt);
			ResultBean bean =this.accountPayService.pay(account);
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void pay(){
		try {
			AccountPayBean account = new AccountPayBean();
			account.setMemberId("100000000001003");
			account.setTn("161118061500000602");
			account.setTxnAmt("1");
			ResultBean bean =this.accountPayService.pay(account);
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
