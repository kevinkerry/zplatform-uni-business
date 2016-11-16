package com.zlebank.zplatform.business.member.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;

/***
 * 绑订手机设置
 * @author SunXiaoshi
 *
 */
public interface BindMobileService {
	/**
	 * 校验解绑手机信息
	 * @param memberId 会员号
	 * @param phone 原手机号
	 * @param payPwd 支付密码
	 * @param smsCode 短信验证码
	 * @return
	 * @throws CommonException
	 * @throws Exception
	 */
	public ResultBean vaildateUnbindPhone(String memberId,String phone,String payPwd,String smsCode) ;
	
	/**
	 * 校验并更新会员绑定的手机号
	 * @param memberId 会员号
	 * @param phone 新手机号
	 * @param smsCode 短信验证码
	 * @throws CommonException
	 */
	public ResultBean modifyPhone(String memberId,String phone,String smsCode) ;
	/**
	 * 校验绑卡信息和支付密码（修改手机号而原手机号不可用）
	 * @param memberId 会员号
	 * @param bindId  绑卡标示
	 * @param cardNo 银行卡号
	 * @param certNo 身份证号
	 * @param payPwd 支付密码
	 * @throws CommonException
	 * @throws Exception
	 */
	public ResultBean vaildateBankCardForModifyPhone(String memberId,long bindId,String cardNo,String certNo,String payPwd);
}
