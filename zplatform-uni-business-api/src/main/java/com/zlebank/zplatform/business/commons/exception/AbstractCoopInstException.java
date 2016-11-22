package com.zlebank.zplatform.business.commons.exception;

import java.util.ResourceBundle;

public abstract class AbstractCoopInstException extends AbstractDescribeException{
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 8484664176033605192L;
	private static final  ResourceBundle RESOURCE = ResourceBundle.getBundle("business_exception");
	@Override
	public ResourceBundle getResourceBundle() {
		return RESOURCE;
	}
}
