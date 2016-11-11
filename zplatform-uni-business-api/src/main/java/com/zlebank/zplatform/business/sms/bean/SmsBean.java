package com.zlebank.zplatform.business.sms.bean;

import java.io.Serializable;

public class SmsBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//手机号
	private String mobile;
	//短信模板
	private String moduleType;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}	
	
}
