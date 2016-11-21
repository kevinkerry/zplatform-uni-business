package com.zlebank.zplatform.business.pay.bean;

import java.io.Serializable;

public class AccountPayBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8303522028697141342L;
	/**
	 * 交易序列号
	 */
	private String txnseqno;
	/**
	 * 受理订单号
	 */
	private String tn;
	/**
	 * 支付会员号
	 */
	private String memberId;
	
	/**
	 * 交易金额
	 */
	private String txnAmt;
	/**
	 * @return the txnseqno
	 */
	public String getTxnseqno() {
		return txnseqno;
	}
	/**
	 * @param txnseqno the txnseqno to set
	 */
	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}
	/**
	 * @return the tn
	 */
	public String getTn() {
		return tn;
	}
	/**
	 * @param tn the tn to set
	 */
	public void setTn(String tn) {
		this.tn = tn;
	}
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * @return the txnAmt
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	
	
	
}
