package com.zlebank.zplatform.business.member.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;

public interface LoginPwdService {
	/**
	 * 验证登录密码 
	 * @param memberId
	 * @param pwd
	 * @return
	 */
	public ResultBean vaildatePwd(String memberId,String pwd) ;
	/**
	 * 修改登录密码
	 * @param memberId 会员号
	 * @param orgPwd 原始密码
	 * @param pwd 新密码
	 * @return
	 */
	public ResultBean modifyPwd(String memberId,String orgPwd,String pwd);
	
	/**
	 * 重置登录密码 
	 * @param memberId 会员号
	 * @param pwd 登录密码 
	 * @param smsCode 短信验证码
	 * @return
	 */
	public ResultBean resetPwd(String memberId,String pwd,String smsCode);
}
