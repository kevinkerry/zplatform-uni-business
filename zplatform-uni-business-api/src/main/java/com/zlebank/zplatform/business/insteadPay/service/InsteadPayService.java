package com.zlebank.zplatform.business.insteadPay.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.InsteadPayImportTypeEnum;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPayInterfaceParamBean;
import com.zlebank.zplatform.business.insteadPay.bean.InsteadPay_Request;
/***
 * 代付相关功能
 * @author liusm
 *
 */
public interface InsteadPayService {
	/***
	 * 批量代付
	 * @param request
	 * @param type
	 * @param param
	 * @return
	 */
	public ResultBean batchInsteadPay(InsteadPay_Request request,InsteadPayImportTypeEnum type,InsteadPayInterfaceParamBean param);
        
}
