package com.zlebank.zplatform.business.commons.enums;

public enum SmsResultEnum {
	UNKNOWN("UNKNOWN","未知"),
	
	S100("100","短信发送成功"),
	S101("101","账号密码不能为空"),
	S102("102","手机号,短信内容均不能为空"),
	S103("103","数据库连接失败"),
	S104("104","账号密码错误"),
	S105("105","短信发送成功,等待审核!"),
	S106("106","短信发送失败"),
	S999("999","未知错误"),
	;

	private String code; // 错误码
	private String msg; // 错误信息

	private SmsResultEnum(String code, String msg) {
		this.code =  code;
		this.msg = msg;
	}

	public static String  getMsg(String code){
		SmsResultEnum message=SmsResultEnum.valueOf(code);
        if(message==null){
        	message=UNKNOWN;
        }
       return message.getMsg();
	};
	
	 public static SmsResultEnum fromValue(String code) {
	        for(SmsResultEnum status:values()){
	            if(status.getCode().equals(code)){
	                return status;
	            }
	        }
	        return SmsResultEnum.UNKNOWN;
	    }
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	
	
	
}
