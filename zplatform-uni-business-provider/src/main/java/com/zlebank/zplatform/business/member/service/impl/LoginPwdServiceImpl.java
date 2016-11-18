package com.zlebank.zplatform.business.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.SmsValidateEnum;
import com.zlebank.zplatform.business.member.service.LoginPwdService;
import com.zlebank.zplatform.member.commons.utils.StringUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.exception.LoginFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("busLoginPwdService")
public class LoginPwdServiceImpl implements LoginPwdService {
	private final static Logger log = LoggerFactory.getLogger(LoginPwdServiceImpl.class);
    @Autowired
	private MemberService memberService;
    @Autowired
    private MemberOperationService memberOperationService; 
    @Autowired
    private ISMSService smsService;
	@Override
	public ResultBean vaildatePwd(String memberId, String pwd) {
		if(memberId==null || pwd == null){
			return new ResultBean("BM0000","参数不能为空");
		}
		PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
		if(pm==null){
			return new ResultBean("BM0008","查询会员失败");
		}
		MemberBean member = new MemberBean();
		member.setLoginName(pm.getLoginName());
		member.setInstiId(pm.getInstiId());
		member.setPhone(pm.getPhone());
		member.setPwd(pwd);
		try {
			String result =memberOperationService.login(MemberType.INDIVIDUAL, member);
			if(StringUtil.isNotEmpty(result)){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0009", "校验密码返回数据为空");
			}
		}catch( LoginFailedException e ){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0010","校验密码失败："+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0011","校验密码异常");
		}
	}

	@Override
	public ResultBean modifyPwd(String memberId, String orgPwd, String pwd) {
		if(memberId==null || pwd == null || orgPwd == null){
			return new ResultBean("BM0000","参数不能为空");
		}
		PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
		if(pm==null){
			return new ResultBean("BM0008","查询会员失败");
		}
		MemberBean member = new MemberBean();
		member.setLoginName(pm.getLoginName());
		member.setInstiId(pm.getInstiId());
		member.setPhone(pm.getPhone());
		member.setPwd(orgPwd);
		try {
			Boolean flag =memberOperationService.resetLoginPwd(MemberType.INDIVIDUAL, member, pwd, true);
			if(flag){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0012","修改密码失败");
			}
		} catch(DataCheckFailedException e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0013","修改密码信息校验失败："+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0014","修改密码异常");
			
		}
		
		
	}

	@Override
	public ResultBean resetPwd(String memberId, String pwd, String smsCode) {
		if(memberId==null || pwd == null || smsCode == null){
			return new ResultBean("BM0000","参数不能为空");
		}
		PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
		if(pm==null){
			return new ResultBean("BM0008","查询会员失败");
		}
		int vcode=0;
		try {
			 vcode = smsService.verifyCode(ModuleTypeEnum.CHANGELOGINPWD, pm.getPhone(), smsCode);
		} catch (Exception e) {
			e.getMessage();
			return new ResultBean("BM0001", "查询短信失败，请重新获取短信验证码");
		}
		SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
		if(valEnum != SmsValidateEnum.SV1){
			return new ResultBean("BM0001", valEnum.getMsg());
		}	
		MemberBean member = new MemberBean();
		member.setLoginName(pm.getLoginName());
		member.setInstiId(pm.getInstiId());
		member.setPhone(pm.getPhone());
		try {
			boolean flag = memberOperationService.resetLoginPwd(MemberType.INDIVIDUAL, member, pwd, false);
			if(flag){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0015","重置密码失败");
			}
		}catch(DataCheckFailedException e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0016","重置密码信息校验失败"+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0017","重置密码异常");
			
		}
	}

}
