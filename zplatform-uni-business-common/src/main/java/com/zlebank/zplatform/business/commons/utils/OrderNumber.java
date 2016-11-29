/* 
 * OrderNumber.java  
 * 
 * version TODO
 *
 * 2015年10月13日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.commons.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.business.commons.dao.CardBinDAO;
import com.zlebank.zplatform.business.commons.service.TxnsLogService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月13日 下午12:21:55
 * @since 
 */
public class OrderNumber {

    private TxnsLogService txnsLogService = (TxnsLogService) SpringContext.getContext().getBean("txnsLogService");

    private static OrderNumber orderNumber;
    
    public static synchronized OrderNumber getInstance(){
        if(orderNumber==null){
            orderNumber = new OrderNumber();
        }
        return orderNumber;
    }
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    private String generateSerialNumber(String sequences){
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) txnsLogService.queryBySQL("select "+sequences+".NEXTVAL seq from dual", new Object[]{});
        DecimalFormat df = new DecimalFormat("00000000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String seqNo = df.format(resultList.get(0).get("SEQ"));
        return sdf.format(new Date())+seqNo;
    }
    
    public  String generateZLOrderId(){
        String seqNo=generateSerialNumber("SEQ_ZLPAY_ORDERNO");
        return seqNo.substring(0,6)+"98"+seqNo.substring(6);
    }
    
    public String generateReaPayOrderId(){
        String seqNo=generateSerialNumber("SEQ_REAPAYORDERNO");
        return seqNo.substring(0,6)+"96"+seqNo.substring(6);
    }
    
    public String generateAppOrderNo(){
        String seqNo=generateSerialNumber("SEQ_APPORDERNO");
        return seqNo.substring(0,6)+"95"+seqNo.substring(6);
    }
    
    public String generateTxnseqno(String busiCode){
        String seqNo=generateSerialNumber("SEQ_TXNSEQNO");
        return seqNo.substring(0,6)+"99"+seqNo.substring(6);
    }
    
    public String generateECITIOrderNo(){
        String seqNo=generateSerialNumber("SEQ_ECITIORDERNO");
        return seqNo.substring(0,6)+"97"+seqNo.substring(6);
    }
    
   
}
