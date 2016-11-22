package com.zlebank.zplatform.business.memberCard.bean;

import java.io.Serializable;

public class QueryCardBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1600861586706026286L;
	private String memberId;
    private String cardType;
    private String devId;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
    
    
}
