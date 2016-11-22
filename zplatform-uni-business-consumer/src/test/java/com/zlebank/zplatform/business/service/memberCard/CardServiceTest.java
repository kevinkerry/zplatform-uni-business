package com.zlebank.zplatform.business.service.memberCard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.QuickCardBean;
import com.zlebank.zplatform.business.memberCard.service.CardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class CardServiceTest {
	
	@Autowired
	private CardService cardService;
	@Test
	public void bindCard(){
		QuickCardBean card = new QuickCardBean();
		card.setRelatememberno("100000000001003");
		card.setAccname("刘玉婷");
		card.setCardno("6217730706921469");
		card.setCardtype("1");
		card.setIdnum("41072198001032453");
		card.setPhone("18210457410");
		try {
			ResultBean bean =this.cardService.bindCard(card);
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void bindCardNew(){
		QuickCardBean card = new QuickCardBean();
		card.setRelatememberno("100000000001003");
		card.setAccname("刘玉");
		card.setCardno("6217730706921467");
		card.setCardtype("1");
		card.setIdnum("41072198001032452");
		card.setPhone("18210457410");
		try {
			ResultBean bean =this.cardService.bindCard(card);
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void unbindCardNew(){
		try {
			ResultBean bean =this.cardService.unBindCard("100000000001003", "220");
			System.out.println(JSON.toJSONString(bean));
		} catch (BusinessCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
