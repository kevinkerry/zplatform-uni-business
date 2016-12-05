package com.zlebank.zplatform.business.sms.bean;

import java.io.Serializable;

public class SmsBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//手机号
	private String mobile;
	//短信模板 1001-注册 1003-修改登录密码 1004-修改支付密码   1005绑卡 
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
