package com.zlebank.zplatform.business.commons.enums;

public enum SmsValidateEnum {
	UNKNOWN("UNKNOWN","未知"),
	
	SV1("1","短信发送成功!"),
	SV2("2","短信校验失败!"),
	SV3("3","请先发送短信!"),
	;

	private String code; // 错误码
	private String msg; // 错误信息

	private SmsValidateEnum(String code, String msg) {
		this.code =  code;
		this.msg = msg;
	}

	public static String  getMsg(String code){
		SmsValidateEnum message=SmsValidateEnum.valueOf(code);
        if(message==null){
        	message=UNKNOWN;
        }
       return message.getMsg();
	};
	
	 public static SmsValidateEnum fromValue(String code) {
	        for(SmsValidateEnum status:values()){
	            if(status.getCode().equals(code)){
	                return status;
	            }
	        }
	        return SmsValidateEnum.UNKNOWN;
	    }
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	
	
	
}
