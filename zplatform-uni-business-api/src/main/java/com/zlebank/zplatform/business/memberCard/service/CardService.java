package com.zlebank.zplatform.business.memberCard.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
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
	public ResultBean  bindCard(QuickCardBean card)throws BusinessCardException;
	/***
	 * 绑卡--短信验证码
	 * @param card
	 * @return
	 */
	public ResultBean  bindCard(QuickCardBean card, String smsCode)throws BusinessCardException;
	/***
	 * 绑卡--短信验证码+支付密码
	 * @param card
	 * @return
	 */
	public ResultBean  bindCard(QuickCardBean card, String smsCode, String payPwd)throws BusinessCardException;
	/****
	 * 解绑银行卡
	 * @param card
	 * @return
	 */
	public ResultBean unBindCard(String memberId, String bindId)throws BusinessCardException;
	/****
	 * 解绑银行卡--支付密码
	 * @param card
	 * @return
	 */
	public ResultBean unBindCardByPayPwd(String memberId, String bindId, String payPwd)throws BusinessCardException;
	
	
	
}
