/* 
 * CardBinBean.java  
 * 
 * version TODO
 *
 * 2016年8月18日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.business.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月18日 下午12:07:02
 * @since
 */
@Entity
@Table(name = "T_CASH_BANK")
public class PojoCashBank implements Serializable {

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
	
	@Column(name="TID")
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	@Column(name="CHNLCODE")
	public String getChnlcode() {
		return chnlcode;
	}
	public void setChnlcode(String chnlcode) {
		this.chnlcode = chnlcode;
	}
	@Column(name="CASHCODE")
	public String getCashcode() {
		return cashcode;
	}
	public void setCashcode(String cashcode) {
		this.cashcode = cashcode;
	}
	@Column(name="BANKCODE")
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	@Column(name="BANKNAME")
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	@Column(name="CARDTYPE")
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	@Column(name="BUSICODE")
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	@Column(name="ICO")
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="NOTES")
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="PAYTYPE")
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	
	
	
}
