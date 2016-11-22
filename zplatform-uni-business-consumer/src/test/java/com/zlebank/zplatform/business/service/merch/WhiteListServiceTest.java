package com.zlebank.zplatform.business.service.merch;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.business.exception.BusinessMerchException;
import com.zlebank.zplatform.business.merch.bean.MerchWhiteBean;
import com.zlebank.zplatform.business.merch.service.WhiteListService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class WhiteListServiceTest {
  @Autowired
  private WhiteListService whiteListService;
   @Test
   public void saveWhiteList(){
	   MerchWhiteBean bean = new MerchWhiteBean();
	   bean.setMerchId("200000000000615");
	   bean.setAccName("刘玉");
	   bean.setCardNo("6217730706921467");
	   bean.setIntime(new Date());
	   bean.setInuser(0l);
	   bean.setStatus("0");
	   try {
		 boolean flag =this.whiteListService.saveWhiteList(bean);
		 System.out.println(flag);
	   } catch (BusinessMerchException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
   }
   
   @Test
   public void updateWhiteList(){
	   MerchWhiteBean bean = new MerchWhiteBean();
	   bean.setMerchId("200000000000615");
	   bean.setAccName("刘玉海");
	   bean.setCardNo("6217730706921467");
	   bean.setIntime(new Date());
	   bean.setInuser(0l);
	   bean.setStatus("0");
	   bean.setId(34l);
	   try {
		   boolean flag =this.whiteListService.updateWhiteList(bean);
		   System.out.println(flag);
		} catch (BusinessMerchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
   }
   @Test
   public void deleteWhiteList(){
	   try {
		   boolean flag =this.whiteListService.deleteWhiteList(34l);
		   System.out.println(flag);
		} catch (BusinessMerchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
   
   }
}
