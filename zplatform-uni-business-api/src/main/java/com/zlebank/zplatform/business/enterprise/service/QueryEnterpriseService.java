package com.zlebank.zplatform.business.enterprise.service;

import com.zlebank.zplatform.business.enterprise.bean.EnterpriseBean;
import com.zlebank.zplatform.business.exception.BusinessEnterpriseException;

/***
 * 查询企业相关
 * @author SunXiaoshi
 *
 */
public interface QueryEnterpriseService {
	/**
	 * 查询企业信息
	 * @param memberId
	 * @return
	 */
	public EnterpriseBean queryEnterpriseByMemberId(String memberId)throws BusinessEnterpriseException;
}
