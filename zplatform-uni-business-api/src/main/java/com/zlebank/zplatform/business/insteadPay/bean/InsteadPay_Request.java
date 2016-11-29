/* 
 * InsteadPay_Request.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.insteadPay.bean;

import java.util.List;

/**
 * 代付【请求报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午2:22:28
 * @since 
 */
public class InsteadPay_Request extends BaseMessage{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**批次号**/
    private String batchNo;
    /**订单发送时间**/
    private String txnTime;
    /**总笔数**/
    private String totalQty;
    /**总金额**/
    private String totalAmt;
    /**文件内容【DEFLATE】算法压缩 **/
    private List<InsteadPayFile> fileContent;
    /**保留域**/
    private String reserved;
  
    private String backUrl;
    
    public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public String getTotalQty() {
        return totalQty;
    }
    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }
    public String getTotalAmt() {
        return totalAmt;
    }
    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }
    
    public List<InsteadPayFile> getFileContent() {
        return fileContent;
    }
    public void setFileContent(List<InsteadPayFile> fileContent) {
        this.fileContent = fileContent;
    }
    public String getReserved() {
        return reserved;
    }
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
    public String getBackUrl() {
        return backUrl;
    }
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    
}
