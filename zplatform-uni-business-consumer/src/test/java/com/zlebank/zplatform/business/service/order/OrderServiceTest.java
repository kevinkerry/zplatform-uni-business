package com.zlebank.zplatform.business.service.order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.InstPayOrderBean;
import com.zlebank.zplatform.business.order.bean.OrderBean;
import com.zlebank.zplatform.business.order.bean.RefundOrderBean;
import com.zlebank.zplatform.business.order.bean.WapWithdrawBean;
import com.zlebank.zplatform.business.order.service.OrderService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class OrderServiceTest {
	
	@Autowired
	private OrderService orderService;
	@Test
	public void createConsumeOrder(){
		try {
			OrderBean order = new OrderBean();
			order.setTxnType("17");
			order.setTxnSubType("00");
			order.setBizType("000205");
			order.setCurrencyCode("156");
			order.setChannelType("00");
			order.setCoopInstiId("300000000000014");
			order.setMerAbbr("天翼分集团");
			order.setMerId("200000000000597");
			order.setMerName("天翼分集团");
			order.setTxnAmt("1");
			order.setTxnTime(getTxnTime());
			order.setOrderId(getOrderNo());
			order.setOrderDesc("测试互金");
			order.setOrderTimeout(null);
			order.setPayTimeout(getOrderTimeout());
			order.setFrontUrl("http://www.baidu.com");
			order.setBackUrl("http://www.baidu.com");
			order.setMemberId("100000000001003");
			ResultBean bean =this.orderService.createConsumeOrder(order);//161118061500000595
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessOrderException e) {
			System.out.println(e.getCode()+""+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void recharge(){
		try {
			OrderBean order = new OrderBean();
			order.setTxnType("02");
			order.setTxnSubType("00");
			order.setBizType("000201");
			order.setCurrencyCode("156");
			order.setChannelType("00");
			order.setCoopInstiId("300000000000027");
			order.setMerAbbr("天翼分集团");
			order.setMerId("200000000000615");
			order.setMerName("天翼分集团");
			order.setTxnAmt("251");
			order.setTxnTime(getTxnTime());
			order.setOrderId(getOrderNo());
			order.setOrderDesc("测试互金");
			order.setOrderTimeout(null);
			order.setPayTimeout(getOrderTimeout());
			order.setFrontUrl("http://www.baidu.com");
			order.setBackUrl("http://www.baidu.com");
			order.setMemberId("100000000001003");
			ResultBean bean =this.orderService.recharge(order);//161118061500000597
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessOrderException e) {
			System.out.println(e.getCode()+""+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void refund(){
		RefundOrderBean order = new RefundOrderBean();
		order.setTxnSubType("00");
		order.setTxnType("14");
		order.setBizType("000203");
		order.setCoopInstiId("300000000000014");
		order.setMemberId("100000000001003");
		order.setMerId("200000000000597");
		order.setOrderDesc("退款订单");
		order.setOrderId(getOrderNo());
		order.setOrigOrderId("2016112209288495");
		order.setOrigTN("161122059700000656");
		order.setTxnAmt("1");
		order.setTxnTime(getTxnTime());
		try {
			this.orderService.createRefundOrder(order);
		} catch (BusinessOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void withdraw(){
		WapWithdrawBean order = new WapWithdrawBean();
		order.setTxnSubType("00");
		order.setTxnType("07");
		order.setBizType("000204");
		order.setCoopInstiId("300000000000027");
		order.setMemberId("100000000001003");
		order.setMerId("200000000000615");
		order.setAmount("1");
		order.setBindId("221");
		order.setOrderId(getOrderNo());
		order.setAmount("1");
		order.setTxnTime(getTxnTime());
		try {
			this.orderService.withdraw(order);
		} catch (BusinessOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void createInstPay(){
		InstPayOrderBean order = new InstPayOrderBean();
		order.setTxnSubType("00");
		order.setTxnType("06");
		order.setBizType("000210");
		order.setCoopInstiId("300000000000014");
		order.setOrderId(getOrderNo());
		order.setTxnTime(getTxnTime());
		order.setMerId("200000000000597");
		order.setAccType("01");
		order.setAccName("刘班");
		order.setAccNo("6217730706921465");
		order.setTxnAmt("1");
		order.setBackUrl("http://www.baidu.com");
		order.setCertifId("410726198801032462");
		order.setCertifTp("02");
		order.setCurrencyCode("156");
		try {
			this.orderService.createInsteadPayOrder(order);
		} catch (BusinessOrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getOrderNo(){
		String time = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String orderNo=time + ((100001+new Integer(new Random().nextInt(899999)))+"").toString();
        return orderNo;
	}
	public String getTxnTime(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	public String getOrderTimeout(){
		 Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(new Date()); 
	     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     Date date=calendar.getTime(); 
	    String time= new SimpleDateFormat("yyyyMMddHH").format(date);
	    return time;
	}
	
	
}
