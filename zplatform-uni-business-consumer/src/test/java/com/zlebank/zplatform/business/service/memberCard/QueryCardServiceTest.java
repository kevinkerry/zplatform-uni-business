package com.zlebank.zplatform.business.service.memberCard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.PageVo;
import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.QueryCardBean;
import com.zlebank.zplatform.business.memberCard.bean.QueryCardResultBean;
import com.zlebank.zplatform.business.memberCard.service.QueryCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class QueryCardServiceTest {
	
	@Autowired
	private QueryCardService quryCardService;
	@Test
	public void queryBindCard(){
		try {
			QueryCardBean query =new QueryCardBean();
			query.setMemberId("100000000001003");
			PageVo<QueryCardResultBean> bean =this.quryCardService.queryBindCardList(query, 0, Integer.MAX_VALUE);
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
