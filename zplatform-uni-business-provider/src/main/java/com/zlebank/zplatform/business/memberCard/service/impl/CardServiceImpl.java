package com.zlebank.zplatform.business.memberCard.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.enums.SmsValidateEnum;
import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.QuickCardBean;
import com.zlebank.zplatform.business.memberCard.service.CardService;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.individual.bean.MemberBean;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.individual.bean.RealNameBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberBankCardService;
import com.zlebank.zplatform.member.individual.service.MemberOperationService;
import com.zlebank.zplatform.member.individual.service.MemberService;
import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
@Service("busCardService")
public class CardServiceImpl implements CardService {
	private final static Logger log = LoggerFactory.getLogger(CardServiceImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberBankCardService memberBankCardService;
	@Autowired
	private MemberOperationService memberOperationService;
	@Autowired
	private ISMSService smsService;
	@Override
	public ResultBean bindCard(QuickCardBean card) throws BusinessCardException {
		if(card == null ){
			throw new BusinessCardException("BD0000");
		}
		try {
			QuickpayCustBean quickpayCustBean = BeanCopyUtil.copyBean(QuickpayCustBean.class, card);
			long bindId=memberBankCardService.saveQuickPayCust(quickpayCustBean);
			return new ResultBean(bindId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessCardException("BD0006");
		}
		
	}

	@Override
	public ResultBean bindCard(QuickCardBean card, String smsCode) throws BusinessCardException {
		if(card == null ||smsCode==null){
			throw new BusinessCardException("BD0000");
		}
		try {
			//校验短信
			int vcode =this.smsService.verifyCode(ModuleTypeEnum.BINDCARD, card.getPhone(), smsCode);
			SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
			if(valEnum != SmsValidateEnum.SV1){
				return new ResultBean("BD0002", valEnum.getMsg());
			}
			//校验支付密码
		   PoMemberBean member = memberService.getMbmberByMemberId(card.getRelatememberno(), MemberType.INDIVIDUAL);
	        if (member == null) {// 资金账户不存在
	        	throw new BusinessCardException("BD0003");
	        }
			// 查询实名认证信息
			RealNameBean bean = new RealNameBean();
			bean.setMemberId(card.getRelatememberno());
	        RealNameBean realNameInfo = memberBankCardService.queryRealNameInfo(bean );
	        if(realNameInfo!=null){
	        	String realName=realNameInfo.getRealname();
	        	if(!realName .equals(card.getAccname())){
	        		return new ResultBean("BD0005", "绑卡信息与实名认证信息不匹配");
	        	}
	        	String idCard =realNameInfo.getIdentiNum();
	        	if(!idCard.equals(card.getIdnum())){
	        		return new ResultBean("BD0005", "绑卡信息与实名认证信息不匹配");
	        	}
	        }
			QuickpayCustBean quickpayCustBean = BeanCopyUtil.copyBean(QuickpayCustBean.class, card);
			long bindId=memberBankCardService.saveQuickPayCust(quickpayCustBean);
			return new ResultBean(bindId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessCardException("BD0006");
		}
	
	}

	@Override
	public ResultBean bindCard(QuickCardBean card, String smsCode, String payPwd) throws BusinessCardException {
		try {
			if(card == null || payPwd==null||smsCode==null){
				throw new BusinessCardException("BD0000");
			}
			//校验短信
			int vcode =this.smsService.verifyCode(ModuleTypeEnum.BINDCARD, card.getPhone(), smsCode);
			SmsValidateEnum valEnum =SmsValidateEnum.fromValue(String.valueOf(vcode));
			if(valEnum != SmsValidateEnum.SV1){
				return new ResultBean("BD0002", valEnum.getMsg());
			}
			//校验支付密码
		   PoMemberBean member = memberService.getMbmberByMemberId(card.getRelatememberno(), MemberType.INDIVIDUAL);
	        if (member == null) {// 资金账户不存在
	        	throw new BusinessCardException("BD0003");
	        }
		    MemberBean memberBean = new MemberBean();
	        memberBean.setLoginName(member.getLoginName());
	        memberBean.setInstiId(member.getInstiId());
	        memberBean.setPhone(member.getPhone());
	        memberBean.setPaypwd(payPwd);
	        boolean pwdFlag= this.memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, memberBean);
			if(!pwdFlag){
				return new ResultBean("BD0004", "支付密码校验失败");
			}
			// 查询实名认证信息
			RealNameBean bean = new RealNameBean();
			bean.setMemberId(card.getRelatememberno());
	        RealNameBean realNameInfo = memberBankCardService.queryRealNameInfo(bean );
	        if(realNameInfo!=null){
	        	String realName=realNameInfo.getRealname();
	        	if(!realName .equals(card.getAccname())){
	        		return new ResultBean("BD0005", "绑卡信息与实名认证信息不匹配");
	        	}
	        	String idCard =realNameInfo.getIdentiNum();
	        	if(!idCard.equals(card.getIdnum())){
	        		return new ResultBean("BD0005", "绑卡信息与实名认证信息不匹配");
	        	}
	        }
			QuickpayCustBean quickpayCustBean = BeanCopyUtil.copyBean(QuickpayCustBean.class, card);
			long bindId=memberBankCardService.saveQuickPayCust(quickpayCustBean);
			return new ResultBean(bindId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessCardException("BD0006");
		}
	}

	@Override
	public ResultBean unBindCard(String memberId, String bindId) throws BusinessCardException {
		if(memberId==null || bindId==null){
			throw new BusinessCardException("BD0000");
		}
		//会员
	    PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
        if (pm == null) {
        	 throw new BusinessCardException("BD0003");//查询会员失败
        }
		try {
			 QuickpayCustBean quickpayCustBean = new QuickpayCustBean();
			 quickpayCustBean.setId(Long.valueOf(bindId));
			 quickpayCustBean.setRelatememberno(memberId);
			 memberBankCardService.unbindQuickPayCust(quickpayCustBean);
		} catch (DataCheckFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			 throw new BusinessCardException("BD0007");
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			 throw new BusinessCardException("BD0007");
		}
		return new ResultBean(true);
		
	}

	@Override
	public ResultBean unBindCardByPayPwd(String memberId, String bindId, String payPwd) throws BusinessCardException {
		if(memberId==null || bindId==null || payPwd==null){
			throw new BusinessCardException("BD0000");
		}
		//会员
	    PoMemberBean pm = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
        if (pm == null) {
        	 throw new BusinessCardException("BD0002");//查询会员失败
        }
        MemberBean member = new MemberBean();
		member.setLoginName(pm.getLoginName());
		member.setInstiId(pm.getInstiId());
		member.setPhone(pm.getPhone());
		member.setPaypwd(payPwd);
		try {
			Boolean flag= memberOperationService.verifyPayPwd(MemberType.INDIVIDUAL, member);
			if(!flag){
				return new ResultBean("BD0007", "支付密码错误");
			 }
			 QuickpayCustBean quickpayCustBean = new QuickpayCustBean();
			 quickpayCustBean.setId(Long.valueOf(bindId));
			 quickpayCustBean.setRelatememberno(memberId);
			 memberBankCardService.unbindQuickPayCust(quickpayCustBean);
		} catch (DataCheckFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			 throw new BusinessCardException("BD0007");
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			 throw new BusinessCardException("BD0007");
		}
		return new ResultBean(true);
	}

}
