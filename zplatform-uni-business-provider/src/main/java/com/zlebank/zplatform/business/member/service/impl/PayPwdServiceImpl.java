package com.zlebank.zplatform.business.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.SmsValidateEnum;
import com.zlebank.zplatform.business.member.service.PayPwdService;
import com.zlebank.zplatform.member.commons.utils.StringUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberBankCardService;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("busPayPwdService")
public class PayPwdServiceImpl implements PayPwdService {
	private final static Logger log = LoggerFactory.getLogger(LoginPwdServiceImpl.class);
	@Autowired
	private MemberService memberService;
    @Autowired
    private MemberOperationService memberOperationService; 
    @Autowired
    private ISMSService smsService;
    @Autowired
    private MemberBankCardService memberBankCardService;
    
	@Override
	public ResultBean vaildatePayPwd(String memberId, String payPwd) {
		if(memberId==null || payPwd == null){
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
		member.setPaypwd(payPwd);
		try {
			Boolean flag= memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, member);
			if(flag){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0018", "校验支付密码失败");
			}
		} catch (DataCheckFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0019","校验支付密码信息失败："+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0020","校验支付密码异常");
		}
	}

	@Override
	public ResultBean modifyPayPwd(String memberId, String orgPayPwd, String payPwd) {
		if(memberId==null || payPwd == null){
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
		member.setPaypwd(orgPayPwd);
		try {
			Boolean flag =null;
			//修改
			if(StringUtil.isNotEmpty(orgPayPwd)){
				flag = memberOperationService.resetPayPwd(MemberType.INDIVIDUAL, member, payPwd, true);
			//设置
			} else {
				flag = memberOperationService.resetPayPwd(MemberType.INDIVIDUAL, member, payPwd, false);
			}
			if(flag){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0021", "修改支付密码失败");
			}
		} catch(DataCheckFailedException e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0022","修改支付密码信息校验失败："+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0023","修改支付密码异常");
			
		}
	}

	@Override
	public ResultBean resetPayPwd(String memberId, String payPwd, String smsCode) {
		if(memberId==null || payPwd == null || smsCode == null){
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
			boolean flag = memberOperationService.resetPayPwd(MemberType.INDIVIDUAL, member, payPwd, true);
			if(flag){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0024","重置支付密码失败");
			}
		}catch(DataCheckFailedException e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0025","重置支付密码信息校验失败："+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0026","重置支付密码异常");
			
		}
	}

	@Override
	public ResultBean vaildateBankCardForResetPwd(String memberId, String phone, String smsCode, long bindId, String cardNo) {
		if(memberId==null || phone == null || smsCode == null || cardNo==null){
			return new ResultBean("BM0000","参数不能为空");
		}
		//获取绑卡信息
		QuickpayCustBean memberBankCard = memberBankCardService.getMemberBankCardById(bindId);
		if(memberBankCard==null){
			return new ResultBean("BM0027", "未找到银行卡信息");
		}
		//校验银行卡号
		if(!cardNo.equals(memberBankCard.getCardno())){
			return new ResultBean("BM0028", "银行卡号错误");
		}
		if(!memberBankCard.getPhone().equals(phone)){
			return new ResultBean("BM0029", "银行卡预留手机号错误");
		}
		//验证短信验证码
		int vcode=0;
		try {
			vcode = smsService.verifyCode(ModuleTypeEnum.RESETPAYPWD, phone, smsCode);
		} catch (Exception e) {
			e.getMessage();
			return new ResultBean("BM0001", "查询短信失败，请重新获取短信验证码");
		}
		SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
		if(valEnum != SmsValidateEnum.SV1){
			return new ResultBean("BM0001", valEnum.getMsg());
		}
		return new ResultBean(true);
	}

}
