package com.zlebank.zplatform.business.exception;

import com.zlebank.zplatform.business.commons.exception.AbstractOrderDescException;

public class BusinessCardException extends AbstractOrderDescException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	
	public BusinessCardException() {
		super();
	}
	public BusinessCardException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }

	public BusinessCardException(String code) {
		super();
		this.code = code;
	}


	@Override
	public String getCode() {
		return this.code;
	}

}
