package com.zlebank.zplatform.business.member.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.SmsValidateEnum;
import com.zlebank.zplatform.business.member.service.BindMobileService;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberBankCardService;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("busBindMobileService")
public class BindMobileServiceImpl implements BindMobileService {
	
	private final static Logger log = LoggerFactory.getLogger(BindMobileServiceImpl.class);
    @Autowired
	private ISMSService smsService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberOperationService memberOperationService;
    @Autowired
    private MemberBankCardService memberBankCardService;
    
	@Override
	public ResultBean vaildateUnbindPhone(String memberId, String phone, String payPwd, String smsCode) {
		if(memberId==null || phone == null || payPwd ==null || smsCode == null){
			return new ResultBean("BM0000","参数不能为空");
		}
		//校验会员
		PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
		if(pm==null){
			return new ResultBean("BM0008","查询会员失败");
		}
		//校验短信验证码
		int vcode = smsService.verifyCode(ModuleTypeEnum.UNBINDPHONE, pm.getPhone(), smsCode);
		SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
		if(valEnum != SmsValidateEnum.SV1){
			return new ResultBean("BM0001", valEnum.getMsg());
		}	
		//校验支付密码
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
				return new ResultBean("BM0010", "校验支付密码失败");
			}
		} catch (DataCheckFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0030","解绑手机信息校验失败"+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0031","校验解绑手机异常");
		}
	}

	@Override
	public ResultBean modifyPhone(String memberId, String phone, String smsCode) {
		try {
			//验证短信验证码
			PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
			if(pm==null){
				return new ResultBean("BM0008","查询会员失败");
			}
			int vcode = smsService.verifyCode(ModuleTypeEnum.BINDPHONE, pm.getPhone(), smsCode);
			SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
			if(valEnum != SmsValidateEnum.SV1){
				return new ResultBean("BM0001", valEnum.getMsg());
			}	
			//获取会员所属合作机构ID
			List<PoMemberBean> memberList = memberService.getMemberByPhoneAndCoopInsti(phone, pm.getInstiId());
			if(memberList.size()>0){
				return new ResultBean("BM0032", "手机号已经被注册 ");
			}
			//更新会员手机号 t_member 
			if(!memberOperationService.modifyPhone(memberId, phone)){
				return new ResultBean("BM0033", "更新手机号失败 ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0034","修改解绑手机异常");
		}
		return new ResultBean(true);
	}

	@Override
	public ResultBean vaildateBankCardForModifyPhone(String memberId, long bindId, String cardNo, String certNo,
			String payPwd) {
		try {
			if(memberId==null || certNo == null || payPwd == null || cardNo==null){
				return new ResultBean("BM0000","参数不能为空");
			}
			//校验会员
			PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
			if(pm==null){
				return new ResultBean("BM0008","查询会员失败");
			}
			//获取绑卡信息
			com.zlebank.zplatform.member.individual.bean.QuickpayCustBean memberBankCard = memberBankCardService.getMemberBankCardById(bindId);
			if(memberBankCard==null){
				return new ResultBean("BM0027", "未找到银行卡信息");
			}
			//校验银行卡号
			if(!cardNo.equals(memberBankCard.getCardno())){
				return new ResultBean("BM0028", "银行卡号错误");
			}
			//校验身份证
			if(!memberBankCard.getIdnum().equals(certNo)){
				return new ResultBean("BM0035", "身份证号错误");
			}
			//校验支付密码
			MemberBean member = new MemberBean();
			member.setLoginName(pm.getLoginName());
			member.setInstiId(pm.getInstiId());
			member.setPhone(pm.getPhone());
			member.setPaypwd(payPwd);
			Boolean flag= memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, member);
			if(flag){
				return new ResultBean(true);
			}else{
				return new ResultBean("BM0016", "校验支付密码失败");
			}
		} catch (DataCheckFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0036","绑卡信息和支付密码相关数据校验失败："+e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			return new ResultBean("BM0037","绑卡信息和支付密码校验异常");
		}
	}

}
