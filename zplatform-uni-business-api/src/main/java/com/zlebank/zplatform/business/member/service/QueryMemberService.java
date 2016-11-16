package com.zlebank.zplatform.business.member.service;

import com.zlebank.zplatform.business.exception.BusinessMemberException;
import com.zlebank.zplatform.business.member.bean.MemberBean;

public interface QueryMemberService {
	/**
	 * 会员信息查询
	 * @param loginName 登录名
	 * @param coopInstiCode 合作机构代码
	 * @return
	 */
	public MemberBean queryMember(String loginName,String coopInstiCode)throws BusinessMemberException;
	
	/**
	 * 查询会员信息（个人会员）
	 * @param memberId
	 * @return
	 * @throws CommonException
	 */
	public MemberBean queryPersonMember(String memberId)throws BusinessMemberException;
}
