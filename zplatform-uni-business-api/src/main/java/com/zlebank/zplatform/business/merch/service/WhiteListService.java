package com.zlebank.zplatform.business.merch.service;

import com.zlebank.zplatform.business.exception.BusinessMerchException;
import com.zlebank.zplatform.business.merch.bean.MerchWhiteBean;

public interface WhiteListService {
	/**
	 * 保存白名单信息
	 * @param merchWhiteList
	 * @return
	 */
	public boolean saveWhiteList(MerchWhiteBean merchWhiteList)  throws BusinessMerchException;
	
	/**
	 * 更新白名单
	 * @param merchWhiteList
	 * @return
	 */
	public boolean updateWhiteList(MerchWhiteBean merchWhiteList)  throws BusinessMerchException;
	
	/**
	 * 删除白名单
	 * @param id
	 * @return
	 */
	public boolean deleteWhiteList(Long id)  throws BusinessMerchException;
}
