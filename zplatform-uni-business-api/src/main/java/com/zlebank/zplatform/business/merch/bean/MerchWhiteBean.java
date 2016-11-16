package com.zlebank.zplatform.business.merch.bean;

import java.io.Serializable;
import java.util.Date;

public class MerchWhiteBean implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7798109980946185175L;
	/**标识**/
    private Long id;
    /**商户会员号**/
    private String merchId;
    /**银行卡号**/
    private String cardNo;
    /**持卡人姓名**/
    private String accName;
    /**0:未通过实名认证（默认状态）1:通过实名认证9:无效信息**/
    private String status;
    /**创建人**/
    private Long inuser;
    /**创建时间**/
    private Date intime;
    /**修改人**/
    private Long upuser;
    /**修改时间**/
    private Date uptime;
    /**备注**/
    private String notes;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the merchId
	 */
	public String getMerchId() {
		return merchId;
	}
	/**
	 * @param merchId the merchId to set
	 */
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the accName
	 */
	public String getAccName() {
		return accName;
	}
	/**
	 * @param accName the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the inuser
	 */
	public Long getInuser() {
		return inuser;
	}
	/**
	 * @param inuser the inuser to set
	 */
	public void setInuser(Long inuser) {
		this.inuser = inuser;
	}
	/**
	 * @return the intime
	 */
	public Date getIntime() {
		return intime;
	}
	/**
	 * @param intime the intime to set
	 */
	public void setIntime(Date intime) {
		this.intime = intime;
	}
	/**
	 * @return the upuser
	 */
	public Long getUpuser() {
		return upuser;
	}
	/**
	 * @param upuser the upuser to set
	 */
	public void setUpuser(Long upuser) {
		this.upuser = upuser;
	}
	/**
	 * @return the uptime
	 */
	public Date getUptime() {
		return uptime;
	}
	/**
	 * @param uptime the uptime to set
	 */
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
    
    


}
