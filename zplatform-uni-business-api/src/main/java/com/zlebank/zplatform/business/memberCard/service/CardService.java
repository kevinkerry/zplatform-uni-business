package com.zlebank.zplatform.business.memberCard.service;

import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.QuickCardBean;
import com.zlebank.zplatform.business.memberCard.bean.UnBindCardBean;

/***
 * 用户绑卡相关
 * @author liusm
 *
 */
public interface CardService {
	/***
	 * 绑卡
	 * @param card
	 * @return
	 */
	public String  bindCard(QuickCardBean card)throws BusinessCardException;
	/****
	 * 解绑银行卡
	 * @param card
	 * @return
	 */
	public void unBindCard(String memberId, String bindId)throws BusinessCardException;
	
	
}
