package com.zlebank.zplatform.business.memberCard.service;

import com.zlebank.zplatform.business.memberCard.bean.CreditCardBean;
import com.zlebank.zplatform.business.memberCard.bean.DebitCardBean;
import com.zlebank.zplatform.business.memberCard.bean.UnBindCardBean;

/***
 * 用户绑卡相关
 * @author liusm
 *
 */
public interface cardService {
	/***
	 * 借记卡绑卡
	 * @param card
	 * @return
	 */
	public String  bindCard(DebitCardBean card);
	/****
	 * 信用卡绑卡
	 * @param card
	 * @return
	 */
	public String bindCard(CreditCardBean card);
	/****
	 * 解绑银行卡
	 * @param card
	 * @return
	 */
	public String unBindCard(UnBindCardBean card);
	
	
}
