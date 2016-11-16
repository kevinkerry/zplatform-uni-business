package com.zlebank.zplatform.business.authority.service;

import java.util.List;

import com.zlebank.zplatform.business.authority.bean.MerchAuthorityBean;
import com.zlebank.zplatform.business.authority.bean.QueryMerchAuthorityBean;

public interface MerchAuthorityService {
	/***
	 * 查询商户权限
	 * @param query
	 * @return
	 */
	public List<MerchAuthorityBean> queryMerchAuthority(QueryMerchAuthorityBean query);
}
