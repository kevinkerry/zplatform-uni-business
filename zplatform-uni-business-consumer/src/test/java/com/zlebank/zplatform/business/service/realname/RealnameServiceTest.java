package com.zlebank.zplatform.business.service.realname;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessOrderException;
import com.zlebank.zplatform.business.exception.BusinessRealnameException;
import com.zlebank.zplatform.business.order.bean.OrderResultBean;
import com.zlebank.zplatform.business.order.service.QueryOrderService;
import com.zlebank.zplatform.business.realname.bean.RealNameBean;
import com.zlebank.zplatform.business.realname.service.RealnameService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class RealnameServiceTest {
	
	@Autowired
	private RealnameService realnameService;
	@Test
	public void realname(){
		try {
			RealNameBean realname= new RealNameBean();
			realname.setPhone("18210457410");
			realname.setCardNo("6217730706921466");
			realname.setCardType("1");
			realname.setCertNo("41072198001032452");
			realname.setCardKeeper("刘玉");
			ResultBean bean =this.realnameService.realname(realname);
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessRealnameException e) {
			System.out.println(e.getCode()+""+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
