package com.zlebank.zplatform.business.commons.dao;

import com.zlebank.zplatform.business.pojo.PojoMerchWhiteList;

public interface MerchWhiteListDAO extends BaseDAO<PojoMerchWhiteList>{
	  /**
     * 得到指定的白名单信息
     * @param accNo
     * @param accName
     */
    public PojoMerchWhiteList getWhiteListByCardNoAndName(String merId, String accNo, String accName);
    
    /**
     * 通过ID获取商户白名单信息
     * @param id
     * @return
     */
    public PojoMerchWhiteList getMerchWhiteListById(Long id);

	public String checkMerchWhiteList(String merId, String accName, String accNo);
}
