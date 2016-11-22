package com.zlebank.zplatform.business.exception;

import com.zlebank.zplatform.business.commons.exception.AbstractRealnameDescException;

public class BusinessEnterpriseException extends AbstractRealnameDescException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	
	public BusinessEnterpriseException() {
		super();
	}
	public BusinessEnterpriseException(String code,Object ... para ) {
        this.params = para;
        this.code = code;
    }

	public BusinessEnterpriseException(String code) {
		super();
		this.code = code;
	}


	@Override
	public String getCode() {
		return this.code;
	}

}
