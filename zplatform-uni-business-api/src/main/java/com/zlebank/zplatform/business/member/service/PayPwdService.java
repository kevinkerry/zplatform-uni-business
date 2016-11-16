package com.zlebank.zplatform.business.member.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;

public interface PayPwdService {
	/**
	 * 验证支付密码  Verify the pay password
	 * @param memberId 会员号
	 * @param payPwd 支付密码
	 * @return
	 * @throws DataCheckFailedException 
	 */
	public ResultBean vaildatePayPwd(String memberId,String payPwd);
	
	/**
     * Modify the pay password.<p>Note:The up layer which invoker the method must make
     * sure that member has login</p>
	 * 修改支付密码
	 * @param memberId 会员号
	 * @param orgPayPwd 原始支付密码
	 * @param payPwd 新支付密码
	 * @return
	 * @throws DataCheckFailedException 
	 */
	public ResultBean modifyPayPwd(String memberId,String orgPayPwd,String payPwd) ;
	/**
	 * 重置支付密码 Reset pay password.No need to make sure that member has login
	 * @param memberId 会员号
	 * @param payPwd 支付密码
	 * @param smsCode 短信验证码
	 * @return
	 * @throws DataCheckFailedException 
	 */
	public ResultBean resetPayPwd(String memberId,String payPwd,String smsCode);
	/**
	 * 校验绑卡信息和短信验证码（实名后重置支付密码）
	 * @param memberId 会员号
	 * @param phone 手机号
	 * @param smsCode 短信验证码
	 * @param bindId 绑卡标示
	 * @param cardNo 银行卡号
	 */
	public ResultBean vaildateBankCardForResetPwd(String memberId,String phone,String smsCode,long bindId,String cardNo);
}
