package com.zlebank.zplatform.business.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.SmsValidateEnum;
import com.zlebank.zplatform.business.member.bean.RegisterMemberBean;
import com.zlebank.zplatform.business.member.service.MemberService;
import com.zlebank.zplatform.member.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.coopinsti.bean.CoopInsti;
import com.zlebank.zplatform.member.coopinsti.service.CoopInstiService;
import com.zlebank.zplatform.member.exception.CreateBusiAcctFailedException;
import com.zlebank.zplatform.member.exception.CreateMemberFailedException;
import com.zlebank.zplatform.member.exception.InvalidMemberDataException;
import com.zlebank.zplatform.member.exception.LoginFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PersonBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("busMemberService")
public class MemberServiceImpl implements MemberService {
	private final static Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	@Autowired
	private MemberOperationService memberOperationService;
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private ISMSService smsService;

	@Override
	public ResultBean register(RegisterMemberBean registerMemberInfo) {
		if(registerMemberInfo == null ){
			return new ResultBean("BM0000", "参数不为空");
		}
		// 机构号转换为机构ID
		CoopInsti coopInsti = coopInstiService.getInstiByInstiCode(registerMemberInfo.getCoopInstiCode());
		if(coopInsti==null){
			return new ResultBean("BM0002", "合作机构不存在");
		}
		String memberId = null;
		PersonBean member= BeanCopyUtil.copyBean(PersonBean.class, registerMemberInfo);
		member.setInstiId(coopInsti.getId());
		try {
			memberId = memberOperationService.registMember(MemberType.INDIVIDUAL, member);
			return new ResultBean(memberId);
		} catch (InvalidMemberDataException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0003", "非法会员信息："+e.getMessage());
		} catch (CreateMemberFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0004", "会员注册失败");
		} catch (CreateBusiAcctFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0005", "会员注册账户处理失败");
		}
	}

	@Override
	public ResultBean register(RegisterMemberBean registerMemberInfo, String smsCode) {
		if(registerMemberInfo == null || smsCode == null){
			return new ResultBean("BM000", "参数为空");
		}
		//校验短信
		int vcode =this.smsService.verifyCode(ModuleTypeEnum.REGISTER.getCode(), registerMemberInfo.getPhone(), smsCode);
		SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
		if(valEnum != SmsValidateEnum.SV1){
			return new ResultBean("BM0001", valEnum.getMsg());
		}		
		// 机构号转换为机构ID
		CoopInsti coopInsti = coopInstiService.getInstiByInstiCode(registerMemberInfo.getCoopInstiCode());
		if(coopInsti==null){
			return new ResultBean("BM0002", "合作机构有误");
		}
		String memberId = null;
		MemberBean member= BeanCopyUtil.copyBean(MemberBean.class, registerMemberInfo);
		member.setInstiId(coopInsti.getId());
		try {
			memberId = memberOperationService.registMember(MemberType.INDIVIDUAL, member);
			return new ResultBean(memberId);
		} catch (InvalidMemberDataException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0003", "非法会员信息："+e.getMessage());
		} catch (CreateMemberFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0004", "会员注册失败");
		} catch (CreateBusiAcctFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0005", "会员注册账户处理失败");
		}
	}
	
	@Override
	public ResultBean login(String loginName, String pwd, String coopInstiCode)  {
		if(loginName==null || pwd==null || coopInstiCode==null){
			return new ResultBean("BM000", "参数为空");
		}
		MemberBean member = new MemberBean();
		member.setLoginName(loginName);
		member.setPwd(pwd);
		CoopInsti coopInsti = coopInstiService.getInstiByInstiCode(coopInstiCode);
		if(coopInsti ==null){
			return new ResultBean("BM0002", "合作机构有误");
		}
		member.setInstiId(coopInsti.getId());
		String memberId = null;
		try {
			memberId=  memberOperationService.login(MemberType.INDIVIDUAL,member);
			return new ResultBean(memberId);
		} catch(LoginFailedException e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0006", "登录失败："+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0007", "登录异常");
		}
	    
	
	}
}
