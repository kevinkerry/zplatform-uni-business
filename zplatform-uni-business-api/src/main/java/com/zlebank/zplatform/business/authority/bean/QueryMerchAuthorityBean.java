package com.zlebank.zplatform.business.authority.bean;

import java.io.Serializable;

public class QueryMerchAuthorityBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String merchId;
	private String coopInstId;
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getCoopInstId() {
		return coopInstId;
	}
	public void setCoopInstId(String coopInstId) {
		this.coopInstId = coopInstId;
	}
	
  
}
