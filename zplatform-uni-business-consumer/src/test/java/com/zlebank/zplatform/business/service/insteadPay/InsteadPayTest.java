package com.zlebank.zplatform.business.service.insteadPay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.InsteadPayImportTypeEnum;
import com.zlebank.zplatform.business.exception.BusinessInsteadPayException;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayFile;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayInterfaceParamBean;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQuery_Request;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQuery_Response;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPay_Request;
import com.zlebank.zplatform.business.insteadPay.service.InsteadPayService;
import com.zlebank.zplatform.business.insteadPay.service.QueryInsteadPayService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class InsteadPayTest {
	@Autowired
	private InsteadPayService insteadPayService;
	@Autowired
	private QueryInsteadPayService queryInsteadPayService;
    @Test
	public void testInsteadPay(){
    	InsteadPay_Request request = new InsteadPay_Request();
    	List<InsteadPayFile> fileContent= new ArrayList<InsteadPayFile>();
    	InsteadPayFile file= new InsteadPayFile();
    	file.setMerId("200000000000613");
    	file.setOrderId(getOrderNo());
    	file.setCurrencyCode("156");
    	file.setAmt("1");
    	file.setBizType("000001");
    	file.setAccType("01");
    	file.setAccNo("6226091212413805");
    	file.setAccName("鲁晓帅");
    	file.setBankCode("308521015014");
    	file.setCertifId("131122198809042433");
    	file.setCertifTp("01");
    	file.setPhoneNo("13426342943");
    	file.setBillType("0100");
    	file.setNotes("这是附言");
    	fileContent.add(file);
    	request.setBackUrl("www.baidu.com");
    	request.setBatchNo("0001");
    	request.setBizType("000205");
    	request.setChannelType("00");
    	request.setCoopInstiId("");
    	request.setEncoding("1");
    	request.setFileContent(fileContent);
    	request.setMerId("200000000000613");
    	request.setTotalAmt("1");
    	request.setTotalQty("1");
    	request.setTxnSubType("");
    	request.setTxnTime(getOrderTimeout());
    	request.setTxnType("");
    	request.setVersion("1.0");
    	request.setSignature("");
    	request.setSignMethod("");
    	InsteadPayInterfaceParamBean param=new InsteadPayInterfaceParamBean();
    	param.setFtpFileName("batchFile");
    	param.setOriginalFileName("batchFile");
    	param.setUserId(0l);
    	ResultBean result=this.insteadPayService.batchInsteadPay(request, InsteadPayImportTypeEnum.API, param);
    	System.out.println(JSON.toJSONString(request));
    	System.out.println(JSON.toJSONString(result));
    	/*InsteadPayQuery_Request requestBean=null;
    	InsteadPayQuery_Response responseBean= null;
    	this.queryInsteadPayService.batchInsteadPayQuery(requestBean, responseBean);*/
    }
    
    @Test
   	public void testQueryInsteadPay(){
    	InsteadPayQuery_Request requestBean=new InsteadPayQuery_Request();
    	requestBean.setBatchNo("0001");
    	requestBean.setTxnTime("2016113016");
    	try {
    		InsteadPayQuery_Response responseBean=this.queryInsteadPayService.batchInsteadPayQuery(requestBean);
			System.out.println(JSON.toJSONString(responseBean));
		} catch (BusinessInsteadPayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
    }
    
    
    public String getOrderNo(){
		String time = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String orderNo=time + ((100001+new Integer(new Random().nextInt(899999)))+"").toString();
        return orderNo;
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
