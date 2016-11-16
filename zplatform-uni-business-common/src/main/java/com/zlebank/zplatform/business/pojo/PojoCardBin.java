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
@Table(name = "T_CARD_BIN")
public class PojoCardBin implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3669538939272944183L;
	/** 卡bin **/
	private String cardBin;
	/** 卡长度 **/
	private String cardLen;
	/** kabin长度 **/
	private String binLen;
	/** 卡名 **/
	private String cardName;
	/** 发卡行名称 **/
	private String bankCode;
	/** 卡类型 **/
	private String type;
	
	@Column(name = "CARDBIN")
	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	@Column(name = "CARDLEN")
	public String getCardLen() {
		return cardLen;
	}

	public void setCardLen(String cardLen) {
		this.cardLen = cardLen;
	}
	@Column(name = "BINLEN")
	public String getBinLen() {
		return binLen;
	}

	public void setBinLen(String binLen) {
		this.binLen = binLen;
	}
	@Column(name = "CARDNAME")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	@Column(name = "BANKCODE")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
