package com.zlebank.zplatform.business.memberCard.bean;

import java.io.Serializable;

public class QuerySupporedCardBean implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cardtype;
	  private String paytype;
	  private String status;
	  private String busiCode;
	  private String bankcode;
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	  
  
}
