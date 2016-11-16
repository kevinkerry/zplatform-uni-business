package com.zlebank.zplatform.business.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.exception.BusinessMemberException;
import com.zlebank.zplatform.business.member.bean.MemberBean;
import com.zlebank.zplatform.business.member.service.QueryMemberService;
import com.zlebank.zplatform.business.memberCard.service.impl.QueryCardServiceImpl;
import com.zlebank.zplatform.member.coopinsti.bean.CoopInsti;
import com.zlebank.zplatform.member.coopinsti.service.CoopInstiService;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.bean.enums.RealNameLvType;
import com.zlebank.zplatform.member.individual.service.MemberService;
@Service("memberService")
public class QueryMemberServiceImpl implements QueryMemberService {
	private final static Logger log = LoggerFactory.getLogger(QueryMemberServiceImpl.class);
	@Autowired
    private MemberService memberService;
	@Autowired
	private CoopInstiService coopInstiService;
	
	@Override
	public MemberBean queryMember(String loginName, String coopInstiCode) throws BusinessMemberException {
		if(loginName==null || coopInstiCode==null ){
			throw new BusinessMemberException("BM0000");
		}
		CoopInsti coopInsti = coopInstiService.getInstiByInstiCode(coopInstiCode);
	    if(coopInsti == null){
	    	 throw new BusinessMemberException("BM0031");
	    }
	    PoMemberBean pm = memberService.getMemberByLoginNameAndCoopInsti(loginName, coopInsti.getId());
		if(pm==null){
			 throw new BusinessMemberException("BM0008");
		}
		MemberBean member = new MemberBean();
		long memid = pm.getMemId();
		String memberId = pm.getMemberId();
		String memberName=pm.getMemberName();
		String pwd=pm.getPwd();
		String paypwd=pm.getPayPwd();
		RealNameLvType realnameLv=pm.getRealnameLv();
		String phone=pm.getPhone();
		String email=pm.getEmail();
		String memberType=pm.getMemberType().getCode();
		String memberStatus=pm.getStatus().getCode();
		String registerIdent=pm.getRegisterIdent();
		member.setMemid(memid+"");
		member.setMemberId(memberId);
		member.setCoopInstiCode(coopInsti.getInstiCode()+"");
		member.setMemberName(memberName);
		member.setPwd(pwd);
		member.setPaypwd(paypwd);
		member.setRealnameLv(realnameLv.getCode());
		member.setPhone(phone);
		member.setEmail(email);
		member.setMemberType(memberType);
		member.setMemberStatus(memberStatus);
		member.setRegisterIdent(registerIdent);
		return member;
		
	}

	@Override
	public MemberBean queryPersonMember(String memberId) throws BusinessMemberException {
		if(memberId==null){
			throw new BusinessMemberException("BM0000");
		}
		PoMemberBean pm  =this.memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
		if(pm==null){
			 throw new BusinessMemberException("BM0008");
		}
		MemberBean member = new MemberBean();
		long memid = pm.getMemId();
		String memberName=pm.getMemberName();
		String pwd=pm.getPwd();
		String paypwd=pm.getPayPwd();
		RealNameLvType realnameLv=pm.getRealnameLv();
		String phone=pm.getPhone();
		String email=pm.getEmail();
		String memberType=pm.getMemberType().getCode();
		String memberStatus=pm.getStatus().getCode();
		String registerIdent=pm.getRegisterIdent();
		member.setMemid(memid+"");
		member.setMemberId(memberId);
		member.setMemberName(memberName);
		member.setPwd(pwd);
		member.setPaypwd(paypwd);
		member.setRealnameLv(realnameLv.getCode());
		member.setPhone(phone);
		member.setEmail(email);
		member.setMemberType(memberType);
		member.setMemberStatus(memberStatus);
		member.setRegisterIdent(registerIdent);
		return member;
	}

}
