package com.zlebank.zplatform.business.memberCard.bean;

import java.io.Serializable;

public class QueryCashBankResultBean implements Serializable {


	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3669538939272944183L;
	private Long tid;
	private String chnlcode;
	private String cashcode;
	private String bankcode;
	private String bankname;
	private String cardtype;
	private String busicode;
	private String ico;
	private String remarks;
	private String notes;
	private String status;
	private String paytype;
	
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public String getChnlcode() {
		return chnlcode;
	}
	public void setChnlcode(String chnlcode) {
		this.chnlcode = chnlcode;
	}
	public String getCashcode() {
		return cashcode;
	}
	public void setCashcode(String cashcode) {
		this.cashcode = cashcode;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	
	
	

}
