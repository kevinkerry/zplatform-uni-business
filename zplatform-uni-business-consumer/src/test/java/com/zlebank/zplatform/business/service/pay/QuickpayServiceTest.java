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
import com.zlebank.zplatform.business.pay.bean.PayBean;
import com.zlebank.zplatform.business.pay.service.QuickPayService;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;
import com.zlebank.zplatform.business.realname.service.RealnameService;
import com.zlebank.zplatform.business.sms.service.SmsService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class QuickpayServiceTest {
	
	@Autowired
	private QuickPayService  quickPayService;
	@Test
	public void payBySmsCode(){
		try {
			PayBean pay= new PayBean();
			pay.setPhone("18210457410");
			pay.setCardNo("6217730706921466");
			pay.setCardType("1");
			pay.setCertNo("41072198001032452");
			pay.setCardKeeper("刘玉");
			pay.setTn("161118061500000595");
			pay.setTxnAmt("1");
			ResultBean bean =this.quickPayService.payBySmsCode(pay, "867147");
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void pay(){
		try {
			PayBean pay= new PayBean();
			pay.setPhone("18210457410");
			pay.setCardNo("6217730706921466");
			pay.setCardType("1");
			pay.setCertNo("41072198001032452");
			pay.setCardKeeper("刘玉");
			pay.setTn("161118061500000595");
			pay.setTxnAmt("1");
			ResultBean bean =this.quickPayService.pay(pay, "100000000001003", "195744", "123456");
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void payByBindIdExt(){
		try {
			PayBean pay= new PayBean();
			pay.setTn("161118061500000597");
			pay.setBindId("219");
			pay.setTxnAmt("1");
			ResultBean bean =this.quickPayService.pay(pay, "100000000001003", "666962", "123456");
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void payByBindId(){
		try {
			PayBean pay= new PayBean();
			pay.setTn("161118061500000598");
			pay.setBindId("219");
			pay.setTxnAmt("251");
			ResultBean bean =this.quickPayService.pay(pay);
			System.out.println(JSON.toJSONString(bean));
		}  catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
