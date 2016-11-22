package com.zlebank.zplatform.business.coopinst.service;

import com.zlebank.zplatform.business.coopinst.bean.CoopInstMkBean;
import com.zlebank.zplatform.business.exception.BusinessCoopInstException;

public interface QueryCoopInstService {
	/***
	 * 根据合作机构号和接入类型查询合作机构的密钥
	 * @param coopInstCode
	 * @param terminalAccessType  1:无线  2:个人门户 3:商户门户 4:WAP 5:开放接口
	 * @return
	 */
	public CoopInstMkBean  queryCoopInstMk(String coopInstCode, String terminalAccessType) throws BusinessCoopInstException;
}
