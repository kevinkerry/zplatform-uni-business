package com.zlebank.zplatform.business.service.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.order.bean.OrderResultBean;
import com.zlebank.zplatform.business.order.service.QueryOrderService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class QueryOrderServiceTest {
	
	@Autowired
	private QueryOrderService queryOrderService;
	@Test
	public void queryOrderByTn(){
		try {
			String tn="161118061500000593";
			OrderResultBean bean =this.queryOrderService.queryOrder(tn);
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessOrderException e) {
			System.out.println(e.getCode()+""+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void queryOrderByMerchAndOrderNo(){
		try {
			String merchId="200000000000615";
			String accOrderNo="2016111810695733";
			OrderResultBean bean =this.queryOrderService.queryOrder(merchId, accOrderNo);
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessOrderException e) {
			System.out.println(e.getCode()+""+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
