package com.zlebank.zplatform.business.memberCard.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.business.commons.bean.ResultBean;
import com.zlebank.zplatform.business.commons.constant.Constant;
import com.zlebank.zplatform.business.exception.BusinessCardException;
import com.zlebank.zplatform.business.memberCard.bean.CardBinBean;
import com.zlebank.zplatform.business.memberCard.bean.QuickCardBean;
import com.zlebank.zplatform.business.memberCard.bean.UnBindCardBean;
import com.zlebank.zplatform.business.memberCard.service.CardService;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.exception.UnbindBankFailedException;
import com.zlebank.zplatform.member.individual.bean.PoMemberBean;
import com.zlebank.zplatform.member.individual.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.individual.bean.enums.MemberType;
import com.zlebank.zplatform.member.individual.service.MemberBankCardService;
import com.zlebank.zplatform.member.individual.service.MemberService;
@Service("cardService")
public class CardServiceImpl implements CardService{
	private final static Logger log = LoggerFactory.getLogger(CardServiceImpl.class);
	@Autowired 
	private MemberBankCardService memberBankCardService;
	@Autowired
	private MemberService memberService;
	/*@Autowired
	private CardBinService cardBinService;*/
	@SuppressWarnings("null")
	@Override
	public String bindCard(QuickCardBean card) throws BusinessCardException {
		  if(card==null){
			  throw new BusinessCardException("BD0000");
		  }
		  if(StringUtil.isNotEmpty(card.getRelatememberno())&& !card.getRelatememberno().equals(Constant.ANONYMOUS)){
			  //校验会员信息
		      PoMemberBean member = memberService.getMbmberByMemberId(card.getRelatememberno(), MemberType.INDIVIDUAL);
		      if (member == null) {
		    	   throw new BusinessCardException("BD0001");//会员不存在
		       }
		  }
		  QuickpayCustBean quickCardBean = null;
		  BeanUtils.copyProperties(card, quickCardBean);
		  quickCardBean.setRelatememberno(card.getRelatememberno()==null?Constant.ANONYMOUS:card.getRelatememberno());
		  if(StringUtil.isEmpty(quickCardBean.getCardno())){
			  throw new BusinessCardException("BD0002");//卡号不能为空
		  }
		  /*CardBinBean cardBin = cardBinService.getCard(cardNo);
	      quickpayCustBean.setBankcode(cardBin.getBankCode());
	      quickpayCustBean.setBankname(cardBin.getBankName());*/
		  Long bindId=this.memberBankCardService.saveQuickPayCustExt(quickCardBean);
		  return bindId+"";
	}

	@Override
	public void unBindCard(String memberId, String bindId) throws BusinessCardException {
		try {
			if(StringUtil.isEmpty(memberId)||StringUtil.isEmpty(bindId)){
				 throw new BusinessCardException("BD0000");//参数为空
			}
			//校验会员信息
		    PoMemberBean member = memberService.getMbmberByMemberId(memberId, MemberType.INDIVIDUAL);
		    if (member == null) {
		    	throw new BusinessCardException("BD0001");//会员不存在
		    }
			QuickpayCustBean quickpayCustBean = new QuickpayCustBean();
			quickpayCustBean.setId(Long.valueOf(bindId));
			quickpayCustBean.setRelatememberno(memberId);
			this.memberBankCardService.unbindQuickPayCust(quickpayCustBean);
		} catch (DataCheckFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessCardException("BD0003");//解绑失败
		} catch (UnbindBankFailedException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BusinessCardException("BD0003");//解绑失败
		}
	}

}
