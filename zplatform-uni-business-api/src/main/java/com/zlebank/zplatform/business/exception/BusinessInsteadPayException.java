package com.zlebank.zplatform.business.exception;

import com.zlebank.zplatform.business.commons.exception.AbstractInsteadPayDescException;

public class BusinessInsteadPayException extends AbstractInsteadPayDescException{
	private static final long serialVersionUID = -5479563221139564445L;

	private String code;
	
	public BusinessInsteadPayException() {
		super();
	}
	public BusinessInsteadPayException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }

	public BusinessInsteadPayException(String code) {
		super();
		this.code = code;
	}


	@Override
	public String getCode() {
		return this.code;
	}

}
