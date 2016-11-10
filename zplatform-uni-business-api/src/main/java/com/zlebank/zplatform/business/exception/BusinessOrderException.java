package com.zlebank.zplatform.business.exception;

import com.zlebank.zplatform.business.commons.exception.AbstractOrderDescException;

public class BusinessOrderException extends AbstractOrderDescException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	
	public BusinessOrderException() {
		super();
	}
	public BusinessOrderException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }

	public BusinessOrderException(String code) {
		super();
		this.code = code;
	}


	@Override
	public String getCode() {
		return this.code;
	}

}
