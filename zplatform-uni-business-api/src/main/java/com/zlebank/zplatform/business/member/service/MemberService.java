package com.zlebank.zplatform.business.member.service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.exception.BusinessMemberException;
import com.zlebank.zplatform.business.member.bean.RegisterMemberBean;

public interface MemberService {
	/**
	 * 会员注册 Register a individual member
	 * @param registerMemberInfo 会员信息
	 * @return
	 */
	public ResultBean register(RegisterMemberBean registerMemberInfo) ;
	/**
	 * 会员注册 Register a individual member
	 * @param registerMemberInfo 会员信息
	 * @param smsCode 短信验证码
	 * @return
	 */
	public ResultBean register(RegisterMemberBean registerMemberInfo,String smsCode) ;
	/**
	 * 会员登录 login with login name,login password
	 * @param loginName 登录名
	 * @param pwd 登录密码
	 * @param coopInstiCode 合作机构号
	 * @return 会员号
	 * @throws BusinessMemberException 
	 */
	public ResultBean login(String loginName,String pwd,String coopInstiCode) ;
	

}
