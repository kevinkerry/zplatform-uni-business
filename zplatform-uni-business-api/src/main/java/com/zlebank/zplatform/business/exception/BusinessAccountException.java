package com.zlebank.zplatform.business.exception;

import com.zlebank.zplatform.business.commons.exception.AbstractAccountDescException;

public class BusinessAccountException extends AbstractAccountDescException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	
	public BusinessAccountException() {
		super();
	}
	public BusinessAccountException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }

	public BusinessAccountException(String code) {
		super();
		this.code = code;
	}


	@Override
	public String getCode() {
		return this.code;
	}

}
