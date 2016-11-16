package com.zlebank.zplatform.business.commons.dao;

import java.util.List;
import java.util.Map;

import com.zlebank.zplatform.business.pojo.PojoCashBank;

public interface CashBankDAO extends BaseDAO<PojoCashBank>{
	/****
	 * 列表查询
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<PojoCashBank> getCardList(Map<String, Object> map,
			Integer pageNo, Integer pageSize);
	
	public int getTotal(Map<String, Object> map,
			Integer pageNo, Integer pageSize);
	/***
	 * 根据bankCode获取ICON
	 * @param bankCode
	 * @return
	 */
	public PojoCashBank getBankICON(String bankCode) ;

}
