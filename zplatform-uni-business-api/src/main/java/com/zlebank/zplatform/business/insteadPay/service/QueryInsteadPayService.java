package com.zlebank.zplatform.business.insteadPay.service;

import com.zlebank.zplatform.business.exception.BusinessInsteadPayException;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQuery_Request;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayQuery_Response;
/***
 * 批量代付查询
 * @author liusm
 *
 */
public interface QueryInsteadPayService {
	
	/***
	 * 批量代付查询
	 * @param requestBean
	 * @return
	 * @throws BusinessInsteadPayException
	 */
	InsteadPayQuery_Response  batchInsteadPayQuery(InsteadPayQuery_Request requestBean) throws BusinessInsteadPayException ;
}
