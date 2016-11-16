package com.zlebank.zplatform.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpException;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.business.order.bean.OrderBean;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.payment.order.bean.OrderResultBean;
import com.zlebank.zplatform.sms.utils.HttpRequestParam;
import com.zlebank.zplatform.sms.utils.HttpUtils;

public class Test {
	public static void main(String[] args) {
		/*com.zlebank.zplatform.payment.order.bean.OrderResultBean orderBean=new OrderResultBean();
		orderBean.setMerId("11111");
		OrderBean order=null;
		//BeanUtils.copyProperties(orderBean, order);
		OrderBean order1=BeanCopyUtil.copyBean(OrderBean.class, orderBean);
		System.out.println(JSON.toJSONString(order));
		System.out.println(JSON.toJSONString(order1));*/
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("Name", "trade");
		paramMap.put("Passwd", "trade");
		paramMap.put("Phone", "13581667012");
		paramMap.put("Content", "1111");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.openConnection();
	   String sms_url="http://192.168.101.134/message/sms.http.php";
		String responseContent;
		try {
			responseContent = httpUtils.executeHttpPost(sms_url, setHttpParams(paramMap), "UTF-8");
			httpUtils.closeConnection();
			int inputLine = Integer.valueOf(responseContent);
			System.out.println(inputLine);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private static List<HttpRequestParam> setHttpParams(Map<String, String> paramMap) {
		 List<HttpRequestParam> formparams = new ArrayList<HttpRequestParam>();
	        Set<Map.Entry<String, String>> set = paramMap.entrySet();
	        for (Map.Entry<String, String> entry : set) {
	            formparams.add(new HttpRequestParam(entry.getKey(), entry.getValue()));
	        }
	        return formparams;
	}
	
	
	
}
